package com.sourcery.clinicapp.user.mapper.sqlProvider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider implements ProviderMethodResolver {

    public static String userSearchSQL(
            @Param("search") String search,
            @Param("occupationId") String occupationId,
            @Param("type") String type) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("users")
                .WHERE("LOWER(surname) LIKE CONCAT(#{search}, '%') OR LOWER(name) LIKE CONCAT(#{search}, '%')")
                .ORDER_BY("surname ASC");
        if (occupationId != null) {
            sql.AND().WHERE(" occupation_id=#{occupationId} ");
        }
        if (type != null) {
            sql.AND().WHERE("type=#{type}");
        }
        System.out.println(sql.toString());
        return sql.toString();
    }

//    public static String testSQL() {
//        SQL sql = new SQL().
//        return sql.toString();
//    }

    public static String userCountSQL() {
        return "<script>SELECT COUNT(*) FROM users <if test='type!=null'> WHERE type=#{type}</if></script>";
    }


}
