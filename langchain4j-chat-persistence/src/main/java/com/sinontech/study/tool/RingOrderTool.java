package com.sinontech.study.tool;

import cn.hutool.core.lang.UUID;
import com.sinontech.study.dao.RingOrderDao;
import com.sinontech.study.entity.RingOrder;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RingOrderTool {

    private final RingOrderDao ringOrderDao;

    @Tool("订购视频彩铃")
    public String orderRing(
            @P("彩铃名称") String ringName,
            @P("彩铃类型") String ringType) {
        try {
            if (ringName == null || ringName.isEmpty()) {
                return "请提供彩铃名称";
            }
            if (ringType == null || ringType.isEmpty()) {
                return "请提供彩铃类型";
            }
            RingOrder order = new RingOrder();
            order.setRingName(ringName);
            order.setRingType(ringType);
            order.setStatus("CREATED");
            ringOrderDao.insert(order);
            return String.format("彩铃 '%s' 订购成功，订单ID: %d", ringName, order.getId());
        } catch (Exception e) {
            return "订购失败: " + e.getMessage();
        }
    }

    @Tool("查询用户的所有彩铃订单")
    public String queryUserOrders(@P("用户ID") String userId) {
        try {
            List<RingOrder> orders = ringOrderDao.findByUserId(userId);
            if (orders.isEmpty()) {
                return "该用户没有彩铃订单";
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < orders.size(); i++) {
                RingOrder order = orders.get(i);
                sb.append(i+1)
                        .append(". 订单ID: ")
                        .append(order.getId())
                        .append(", 彩铃: ")
                        .append(order.getRingName())
                        .append(", 类型: ")
                        .append(order.getRingType())
                        .append(", 状态: ")
                        .append(order.getStatus())
                        .append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "查询失败: " + e.getMessage();
        }
    }

    @Tool("取消彩铃订单")
    public String cancelOrder(@P("订单ID") Long orderId) {
        try {
            int result = ringOrderDao.updateStatus(orderId, "CANCELLED");
            return result > 0 ? "订单已取消" : "订单取消失败或不存在";
        } catch (Exception e) {
            return "取消失败: " + e.getMessage();
        }
    }
}
