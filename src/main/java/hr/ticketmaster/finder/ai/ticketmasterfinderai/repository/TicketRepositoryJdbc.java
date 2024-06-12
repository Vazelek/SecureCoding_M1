package hr.ticketmaster.finder.ai.ticketmasterfinderai.repository;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.Ticket;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketFilter;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.TicketTypeEnum;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.utils.ConversionUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
@AllArgsConstructor
public class TicketRepositoryJdbc implements TicketRepository {
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets =
                jdbcTemplate.query("SELECT * FROM TICKET", new TicketResultSetExtractor());
        return tickets;
    }

    @Override
    public Optional<Ticket> findById(Integer id) {
        String sql = "SELECT * FROM TICKET WHERE ID = ?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql, new TicketRowMapper(), id));
    }

    @Override
    public void save(Ticket ticket) {
        Map<String, Object> params = new HashMap<>();
        params.put("TYPE_ID",ticket.getType().getName());
        params.put("EVENT_NAME",ticket.getEventName());
        params.put("VENUE",ticket.getVenue());
        params.put("DESCRIPTION",ticket.getDescription());
        params.put("EVENT_DATE_TIME",ticket.getEventDateTime());
        params.put("PRICE",ticket.getPrice());

        jdbcTemplate.update(
                "INSERT INTO TICKET(TYPE_ID, EVENT_NAME, VENUE, DESCRIPTION, EVENT_DATE_TIME, PRICE) " +
                        "VALUES (:TYPE_ID, :EVENT_NAME, :VENUE, :DESCRIPTION, :EVENT_DATE_TIME, :PRICE)",
                params
        );
    }

    @Override
    public List<Ticket> filterByCriteria(TicketFilter ticketFilter) {
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM TICKET where 1=1 ");
        List<Object> queryArgs = new ArrayList<>();

        if(!ticketFilter.getType().isEmpty()){
            sqlQuery.append("AND type_id = ? ");
            queryArgs.add(TicketTypeEnum.valueOf(ticketFilter.getType()).ordinal());
        }

        if(!ticketFilter.getEventName().isEmpty()){
            sqlQuery.append("AND EVENT_NAME LIKE ('%'||?||'%') ");
            queryArgs.add(ticketFilter.getEventName());
        }

        if(!ticketFilter.getVenue().isEmpty()){
            sqlQuery.append("AND VENUE LIKE ('%'||?||'%') ");
            queryArgs.add(ticketFilter.getVenue());
        }

        if(!ticketFilter.getDescription().isEmpty()){
            sqlQuery.append("AND DESCRIPTION LIKE ('%'||?||'%') ");
            queryArgs.add(ticketFilter.getDescription());
        }

        if(!ticketFilter.getEventDateTimeFromString().isEmpty()){

            LocalDateTime localDateTimeTo = LocalDateTime.parse(ticketFilter.getEventDateTimeFromString(),
                    ConversionUtils.FORMATTER);

            sqlQuery.append("AND EVENT_DATE_TIME >= ? ");
            queryArgs.add(localDateTimeTo);
        }

        /*
        if(Optional.ofNullable(carSearchForm.getProductionYearTo()).isPresent()){
            sqlQuery.append("AND productionyear <= ? ");
            queryArgs.add(carSearchForm.getProductionYearTo());
        }

        if(Optional.ofNullable(carSearchForm.getMilageFrom()).isPresent()){
            sqlQuery.append("AND milage >= ? ");
            queryArgs.add(carSearchForm.getMilageFrom());
        }

        if(Optional.ofNullable(carSearchForm.getMilageTo()).isPresent()){
            sqlQuery.append("AND milage <= ? ");
            queryArgs.add(carSearchForm.getMilageTo());
        }

        if(Optional.ofNullable(carSearchForm.getPriceFrom()).isPresent()){
            sqlQuery.append("AND price >= ? ");
            queryArgs.add(carSearchForm.getPriceFrom());
        }

        if(Optional.ofNullable(carSearchForm.getPriceTo()).isPresent()){
            sqlQuery.append("AND price <= ? ");
            queryArgs.add(carSearchForm.getPriceTo());
        }

        Object[] preparedStatementArgs = new Object[queryArgs.size()];
        for(int i = 0; i < preparedStatementArgs.length; i++){
            preparedStatementArgs[i] = queryArgs.get(i);
        }

         */

        /* this is the part I used from the above stackoverflow question */
        Object[] preparedStatementArgs = new Object[queryArgs.size()];
        for(int i = 0; i < preparedStatementArgs.length; i++){
            preparedStatementArgs[i] = queryArgs.get(i);
        }

        /* Lastly, execute the query */
        return this.jdbcTemplate.query(sqlQuery.toString(), new TicketRowMapper(),
                preparedStatementArgs);

        //return this.jdbcTemplate.query(sqlQuery.toString(), new CarRowMapper(), preparedStatementArgs);
        //return jdbcTemplate.query(sqlQuery.toString(), );
    }

    public Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        Ticket newTicket = new Ticket();
        newTicket.setId(rs.getInt("ID"));

        Optional<TicketTypeEnum> ticketTypeOptional = Arrays.stream(TicketTypeEnum.values()).filter(
                tt -> {
                    try {
                        return tt.ordinal() == rs.getInt("TYPE_ID");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).findFirst();

        //Integer ticketTypeId = rs.getInt("TYPE_ID");

        //ticketTypeOptional.ifPresent(newTicket::setType);

        newTicket.setEventName(rs.getString("EVENT_NAME"));
        newTicket.setVenue(rs.getString("VENUE"));
        newTicket.setDescription(rs.getString("DESCRIPTION"));
        newTicket.setEventDateTime(rs.getTimestamp("EVENT_DATE_TIME").toLocalDateTime());
        newTicket.setPrice(rs.getBigDecimal("PRICE"));

        return newTicket;
    }

    public class TicketRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            return mapResultSetToTicket(rs);
        }
    }

    public class TicketResultSetExtractor implements ResultSetExtractor<List<Ticket>> {

        @Override
        public List<Ticket> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Ticket> tickets = new ArrayList<>();

            while(rs.next()) {
                Ticket newTicket = mapResultSetToTicket(rs);
                tickets.add(newTicket);
            }

            return tickets;
        }
    }
}
