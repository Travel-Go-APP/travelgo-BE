package com.travelgo.backend.domain.event.repository;

import com.travelgo.backend.domain.event.dto.VisitPercent;
import com.travelgo.backend.domain.event.entity.VisitCountBenefit;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BenefitBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<VisitCountBenefit> list) {
        String sql = "INSERT INTO visit_count_benefit (area_code, benefit_ratio, benefit_type, ranking) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        VisitCountBenefit benefit = list.get(i);
                        ps.setString(1, benefit.getAreaCode().toString());
                        ps.setDouble(2, benefit.getBenefitRatio());
                        ps.setString(3, benefit.getBenefitType());
                        ps.setInt(4, benefit.getRanking());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });

    }
}
