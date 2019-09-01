package com.study.mybatis.plugin;

import com.study.mybatis.utils.PageInfo;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: allen
 * @Date: 2019/6/18 20:48
 */
@Intercepts(@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}))
public class StudyPagePlugin implements Interceptor {

    // 数据库的类型
    private static String dialect = "";
    // 分页关键字
    private static String pageSqlId = "";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 确定哪些方法要分页 byPage结尾
        // 从invocation拿到我们StatementHandler对象
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        // 得大搜原始的sql
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        System.out.println("原始sql:" + sql);

        // 分页参数
        Object paramObj = boundSql.getParameterObject();

        MetaObject metaObject = MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());

        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 获取mapper接口中的方法名称  selectUserByPage
        String mapperMethodName = mappedStatement.getId();

        if (mapperMethodName.matches(".*ByPage$")) {
            Map<String, Object> params = (Map<String, Object>) paramObj;

            PageInfo pageInfo = (PageInfo) params.get("page"); // map.put("page", PageInfo);
            //  select * from user;
            String countSql = "select count(0) from (" + sql + ") a";
            System.out.println("查询总数的sql : " + countSql);

            // mybatis执行一个jdbc操作
            Connection connection = (Connection) invocation.getArgs()[0];
            PreparedStatement countStatement = connection.prepareStatement(countSql);
            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
            parameterHandler.setParameters(countStatement);
            ResultSet rs = countStatement.executeQuery();
            if (rs.next()) {
                pageInfo.setTotalNumber(rs.getInt(1));
            }
            rs.close();
            countStatement.close();

            // 改造sql  limit ， count
            String pageSql = generaterPageSql(sql, pageInfo);
            System.out.println("分页sql : " + pageSql); //mybatispulugs pagehepeler

            // 把分页的SQL放回
            // select * from user;
            // select * from user limit 1,3
            metaObject.setValue("delegate.boundSql.sql", pageSql);

        }

        // 把执行流程交给mybatis
        return invocation.proceed();
    }


    // 根据数据库类型生成sql
    // SELECT * FROM USER LIMIT 0, 3;
    public String generaterPageSql(String sql , PageInfo pageInfo) {
//        if (dialect.equals("MySQL")) {
            StringBuffer sb = new StringBuffer();
            sb.append(sql);
            sb.append(" limit " + pageInfo.getStartIndex() + " ," + pageInfo.getTotalSelect());
            return sb.toString();
//        } else if (dialect.equals("Oracle")) {
//
//        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        dialect = properties.getProperty("dialect");
        pageSqlId = properties.getProperty("pageSqlId");
    }

}
