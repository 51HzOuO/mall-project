package mall.mapper;

import java.util.List;
import java.util.Map;
import mall.bean.Furn;
import mall.bean.FurnExample.Criteria;
import mall.bean.FurnExample.Criterion;
import mall.bean.FurnExample;
import org.apache.ibatis.jdbc.SQL;

public class FurnSqlProvider {
    public String countByExample(FurnExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("furn");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(FurnExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("furn");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Furn row) {
        SQL sql = new SQL();
        sql.INSERT_INTO("furn");
        
        if (row.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (row.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (row.getCompany() != null) {
            sql.VALUES("company", "#{company,jdbcType=VARCHAR}");
        }
        
        if (row.getPrice() != null) {
            sql.VALUES("price", "#{price,jdbcType=DECIMAL}");
        }
        
        if (row.getSales() != null) {
            sql.VALUES("sales", "#{sales,jdbcType=INTEGER}");
        }
        
        if (row.getStock() != null) {
            sql.VALUES("stock", "#{stock,jdbcType=INTEGER}");
        }
        
        if (row.getImgPath() != null) {
            sql.VALUES("img_path", "#{imgPath,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExample(FurnExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("name");
        sql.SELECT("company");
        sql.SELECT("price");
        sql.SELECT("sales");
        sql.SELECT("stock");
        sql.SELECT("img_path");
        sql.FROM("furn");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Furn row = (Furn) parameter.get("row");
        FurnExample example = (FurnExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("furn");
        
        if (row.getId() != null) {
            sql.SET("id = #{row.id,jdbcType=INTEGER}");
        }
        
        if (row.getName() != null) {
            sql.SET("name = #{row.name,jdbcType=VARCHAR}");
        }
        
        if (row.getCompany() != null) {
            sql.SET("company = #{row.company,jdbcType=VARCHAR}");
        }
        
        if (row.getPrice() != null) {
            sql.SET("price = #{row.price,jdbcType=DECIMAL}");
        }
        
        if (row.getSales() != null) {
            sql.SET("sales = #{row.sales,jdbcType=INTEGER}");
        }
        
        if (row.getStock() != null) {
            sql.SET("stock = #{row.stock,jdbcType=INTEGER}");
        }
        
        if (row.getImgPath() != null) {
            sql.SET("img_path = #{row.imgPath,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("furn");
        
        sql.SET("id = #{row.id,jdbcType=INTEGER}");
        sql.SET("name = #{row.name,jdbcType=VARCHAR}");
        sql.SET("company = #{row.company,jdbcType=VARCHAR}");
        sql.SET("price = #{row.price,jdbcType=DECIMAL}");
        sql.SET("sales = #{row.sales,jdbcType=INTEGER}");
        sql.SET("stock = #{row.stock,jdbcType=INTEGER}");
        sql.SET("img_path = #{row.imgPath,jdbcType=VARCHAR}");
        
        FurnExample example = (FurnExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Furn row) {
        SQL sql = new SQL();
        sql.UPDATE("furn");
        
        if (row.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (row.getCompany() != null) {
            sql.SET("company = #{company,jdbcType=VARCHAR}");
        }
        
        if (row.getPrice() != null) {
            sql.SET("price = #{price,jdbcType=DECIMAL}");
        }
        
        if (row.getSales() != null) {
            sql.SET("sales = #{sales,jdbcType=INTEGER}");
        }
        
        if (row.getStock() != null) {
            sql.SET("stock = #{stock,jdbcType=INTEGER}");
        }
        
        if (row.getImgPath() != null) {
            sql.SET("img_path = #{imgPath,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, FurnExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}