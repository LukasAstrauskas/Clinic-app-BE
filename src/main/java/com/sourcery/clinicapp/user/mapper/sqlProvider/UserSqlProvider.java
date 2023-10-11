package com.sourcery.clinicapp.user.mapper.sqlProvider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.util.UUID;

public class UserSqlProvider implements ProviderMethodResolver {

    public static String userSearch(
            @Param("search") String search,
            @Param("occupationId") UUID occupationId,
            @Param("type") String type) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("users")
                .WHERE("LOWER(surname) LIKE CONCAT(#{search}, '%') OR LOWER(name) LIKE CONCAT(#{search}, '%')");
//            .OR().WHERE("LOWER(name) LIKE CONCAT(#{search}, '%')");
        if (occupationId != null) {
            sql.AND().WHERE(" occupation_id=#{occupationId} ");
        }
        if (type != null){
            sql.AND().WHERE("type=#{type}");
        }


//            .WHERE(" LOWER(name) LIKE #{search} '%' ");
//            .WHERE( "type='admin'");
//            .WHERE(" LOWER(name) LIKE #{search} + '%' ");
        System.out.println(sql.toString());
        return sql.toString();

    }


}
