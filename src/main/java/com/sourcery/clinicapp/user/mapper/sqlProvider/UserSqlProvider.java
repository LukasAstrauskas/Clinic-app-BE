package com.sourcery.clinicapp.user.mapper.sqlProvider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider implements ProviderMethodResolver {

public static String userSearch(@Param("search") String search){
    SQL sql = new SQL()
            .SELECT("*")
            .FROM("users")
            .WHERE("LOWER(surname) LIKE CONCAT(#{search}, '%')")
            .OR().WHERE("LOWER(name) LIKE CONCAT(#{search}, '%')");
//            .WHERE(" LOWER(name) LIKE #{search} '%' ");
//            .WHERE( "type='admin'");
//            .WHERE(" LOWER(name) LIKE #{search} + '%' ");
    return sql.toString();

}


}
