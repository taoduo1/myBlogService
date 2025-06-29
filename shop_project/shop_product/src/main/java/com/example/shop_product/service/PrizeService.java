package com.example.shop_product.service;

import com.example.shop_product.entity.Prize;
import com.example.shop_common.common.service.CrudService;
import com.example.shop_product.entity.dto.PrizeDto;

/**
 * <p>
 * 奖品表 服务类
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-29
 */
public interface PrizeService extends CrudService<Prize> {

    Prize savePrize(Prize prize);

    void initPrizes(PrizeDto prizeDto);

    Prize drawPrizes(PrizeDto prizeDto);
}
