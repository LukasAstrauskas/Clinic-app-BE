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

    public static String getUsersSQL(
            @Param("search") String search,
            @Param("occupationId") String occupationId,
            @Param("offset") int offset,
            @Param("userType") String userType) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("users")
                .ORDER_BY("surname ASC")
                .LIMIT(5)
                .OFFSET(offset);
        if (search.length() > 0) {
            sql.AND().WHERE("LOWER(surname) LIKE CONCAT(#{search}, '%') OR LOWER(name) LIKE CONCAT(#{search}, '%')");
        }
        if (occupationId != null) {
            sql.AND().WHERE(" occupation_id=#{occupationId} ");
        }
        if (userType != null) {
            sql.AND().WHERE("type=#{userType}");
        }
        System.out.println(UserSqlProvider.class + " " + sql.toString());
        return sql.toString();
    }

    public static String userCountSQL() {
        return "<script>SELECT COUNT(*) FROM users <if test='type!=null'> WHERE type=#{type}</if></script>";
    }


}
