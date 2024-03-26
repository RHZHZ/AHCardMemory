package cn.rhzhz.memorycards.Controller;

import cn.rhzhz.memorycards.Entity.Flashcard;
import cn.rhzhz.memorycards.Entity.plan;
import cn.rhzhz.memorycards.Mapper.cardMapper;
import cn.rhzhz.memorycards.Mapper.planMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//这个注解是会把所有数据渲染成json
@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST},
        allowedHeaders = {"Authorization", "Cache-Control", "Content-Type"})
@RestController
@RequestMapping("/plan")
public class planController {
    @Resource
    planMapper mapper;
    @PostMapping("/add")
    public String planUpdate(@RequestBody plan newPlan){
        try {
            // 调用服务层方法更新卡片
                mapper.planUp(newPlan);
                System.out.println("更新前："+newPlan);
            // 返回更新状态，通常更新操作不返回实体列表
            return "successfully";
        } catch (Exception e) {
            // 如果出现任何异常，返回错误响应
            return "Plan updated failed";
        }
    }
    @GetMapping("/getPlan")
    public plan getPlan(){
        plan plan=mapper.getPlan();
        System.out.println("获取到的计划是:"+plan);
        return plan;
    }
}
