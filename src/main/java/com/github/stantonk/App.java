package com.github.stantonk;

import com.github.stantonk.jooq_sandbox.tables.records.AuthorRecord;
import org.apache.commons.dbutils.DbUtils;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

// For convenience, always static import your generated tables and jOOQ functions to decrease verbosity:
import static com.github.stantonk.jooq_sandbox.Tables.*;
import static org.jooq.impl.DSL.*;

public class App {
    public static void main(String[] args) {
        Connection conn = null;

        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/library";

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(url, userName, password);
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            Result<Record3<String, String, String>> result = ctx
                    .select(AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, BOOK.TITLE)
                    .from(AUTHOR, BOOK)
                    .where(AUTHOR.ID.equal(BOOK.AUTHOR_ID))
                    .fetch();

            for (Record r : result) {
                String firstName = r.getValue(AUTHOR.FIRST_NAME);
                String lastName = r.getValue(AUTHOR.LAST_NAME);
                String title = r.getValue(BOOK.TITLE);

                System.out.println(title + ", By: " + firstName + " " + lastName);
            }

//            AuthorRecord author = new AuthorRecord();
//            author.setId(2);
//            author.setFirstName("Jane");
//            author.setLastName("Doe");
//            author.setYearOfBirth(1985);
//            //TODO author.setDateOfBirth(); hmm, how to safely handle Date objects, timezones?
//            ctx.executeInsert(author);
        } catch (Exception e) {
            // For the sake of this tutorial, let's keep exception handling simple
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
