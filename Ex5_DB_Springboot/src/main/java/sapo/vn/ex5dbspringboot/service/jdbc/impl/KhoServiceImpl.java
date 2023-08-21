package sapo.vn.ex5dbspringboot.service.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sapo.vn.ex5dbspringboot.entity.Kho;
import sapo.vn.ex5dbspringboot.service.jdbc.KhoService;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

@Service
public class KhoServiceImpl implements KhoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Kho> getAllKho() {
        String sql = "SELECT * FROM Kho";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Kho.class));
    }

    @Override
    public Kho createKho(Kho kho) {
        try {
            String sql = "INSERT INTO Kho (MaKho,Ten,DiaDiem,NgayTao,NgaySua) VALUES (?,?,?,?,?)";
            Date currentDate = new Date();
            jdbcTemplate.update(sql,
                    kho.getMaKho(), kho.getTen(), kho.getDiaDiem(), currentDate, currentDate);
            return kho;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Kho updateKho(Kho kho) {
        try {
            String sql = "UPDATE Kho SET MaKho=?,Ten=?,DiaDiem=?,NgaySua=? WHERE Id=?";
            Date currentDate = new Date();
            jdbcTemplate.update(sql,
                    kho.getMaKho(), kho.getTen(), kho.getDiaDiem(), currentDate, kho.getId());
            return kho;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteKho(int id) {
        try {
            String sql = "DELETE * FROM Kho WHERE Id=?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Kho getByIdKho(int id) {
        String sql = "SELECT * FROM Kho WHERE Id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Kho.class));
    }
}
