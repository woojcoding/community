package com.portfolio.community.enums;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * boardType Enum을 처리하기위한 타입 핸들러
 */
@MappedTypes(BoardType.class)
public class BoardTypeHandler implements TypeHandler<BoardType> {

    @Override
    public void setParameter(PreparedStatement ps, int i, BoardType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public BoardType getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);

        return getCodeEnum(code);
    }

    @Override
    public BoardType getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);

        return getCodeEnum(code);
    }

    @Override
    public BoardType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);

        return getCodeEnum(code);
    }

    private BoardType getCodeEnum(String code) {
        switch (code) {
            case "n":
                return BoardType.NOTICE;
            case "f":
                return BoardType.FREE;
            case "h":
                return BoardType.HELP;
            case "g":
                return BoardType.GALLERY;
        }

        return null;
    }
}
