package my.ssm.o2o.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import my.ssm.o2o.dto.GridResult;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.service.AreaService;

@Controller
@RequestMapping("/super_admin")
public class AreaController {
    @Autowired
    private AreaService areaService;
    @GetMapping("/list_area")
    @ResponseBody
    public Result listArea() {
        try {
            List<Area> areaList = areaService.findAll();
            return new GridResult<Area>(areaList, areaList.size());
        } catch (Exception e) {
            return new Result(e.getMessage());
        }
    }
}
