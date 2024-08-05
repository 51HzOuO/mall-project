package mall.mapper;

import java.util.List;
import mall.bean.Furn;
import mall.bean.FurnExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface FurnMapper {
    @SelectProvider(type=FurnSqlProvider.class, method="countByExample")
    long countByExample(FurnExample example);

    @DeleteProvider(type=FurnSqlProvider.class, method="deleteByExample")
    int deleteByExample(FurnExample example);

    @Delete({
        "delete from furn",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into furn (id, name, ",
        "company, price, ",
        "sales, stock, img_path)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{company,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, ",
        "#{sales,jdbcType=INTEGER}, #{stock,jdbcType=INTEGER}, #{imgPath,jdbcType=VARCHAR})"
    })
    int insert(Furn row);

    @InsertProvider(type=FurnSqlProvider.class, method="insertSelective")
    int insertSelective(Furn row);

    @SelectProvider(type=FurnSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="company", property="company", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="sales", property="sales", jdbcType=JdbcType.INTEGER),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER),
        @Result(column="img_path", property="imgPath", jdbcType=JdbcType.VARCHAR)
    })
    List<Furn> selectByExample(FurnExample example);

    @Select({
        "select",
        "id, name, company, price, sales, stock, img_path",
        "from furn",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="company", property="company", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="sales", property="sales", jdbcType=JdbcType.INTEGER),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER),
        @Result(column="img_path", property="imgPath", jdbcType=JdbcType.VARCHAR)
    })
    Furn selectByPrimaryKey(Integer id);

    @UpdateProvider(type=FurnSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("row") Furn row, @Param("example") FurnExample example);

    @UpdateProvider(type=FurnSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("row") Furn row, @Param("example") FurnExample example);

    @UpdateProvider(type=FurnSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Furn row);

    @Update({
        "update furn",
        "set name = #{name,jdbcType=VARCHAR},",
          "company = #{company,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DECIMAL},",
          "sales = #{sales,jdbcType=INTEGER},",
          "stock = #{stock,jdbcType=INTEGER},",
          "img_path = #{imgPath,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Furn row);
}