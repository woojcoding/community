package com.portfolio.community.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * boardType Enum을 처리하기위한 타입 핸들러
 */
@MappedTypes(BoardType.class)
public class BoardTypeHandler extends BaseTypeHandler<BoardType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BoardType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public BoardType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);

        return BoardType.valueOf(value);
    }

    @Override
    public BoardType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);

        return BoardType.valueOf(value);
    }

    @Override
    public BoardType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);

        return BoardType.valueOf(value);
    }
}
