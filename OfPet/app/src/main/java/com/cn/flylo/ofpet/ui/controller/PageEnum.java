package com.cn.flylo.ofpet.ui.controller;


import com.cn.flylo.ofpet.ui.page.common.AgreementFgm;
import com.cn.flylo.ofpet.ui.page.common.InterestedFgm;
import com.cn.flylo.ofpet.ui.page.main.MainFgm;

import com.cn.flylo.ofpet.ui.page.mine.*;
import com.cn.flylo.ofpet.ui.page.prize.*;
import com.cn.flylo.ofpet.ui.page.search.SearchFgm;
import com.cn.flylo.ofpet.ui.page.search.SearchPeopleFgm;
import com.cn.flylo.ofpet.ui.page.search.SearchVideoFgm;
import com.cn.flylo.ofpet.ui.page.task.EditTaskFgm;
import com.cn.flylo.ofpet.ui.page.task.TaskDetailFgm;
import org.jetbrains.annotations.NotNull;

/**
 * @author axw_an
 * @create on 2019/5/28
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/28:
 */
public enum PageEnum {

    Main(MainFgm.class),

    MineInfo(MineInfoFgm.class),
    News(NewsFgm.class),
    Message(MessageFgm.class),
    Buy(BuyFgm.class),
    Earnings(EarningsFgm.class),
    Detail(DetailFgm.class),
    Recharge(RechargeFgm.class),
    Certification(CertificationFgm.class),
    Setting(SettingFgm.class),

    BuyConfirm(BuyConfirmFgm.class),
    RechargeRecord(RechargeRecordFgm.class),

    PrizeList(PrizeListFgm.class),
    PrizeDetails(PrizeDetailsFgm.class),
    ProvidePrize(ProvidePrizeFgm.class),
    EditAward(EditAwardFgm.class),

    TaskDetail(TaskDetailFgm.class),
    EditTask(EditTaskFgm.class),
    Agreement(AgreementFgm.class),

    Search(SearchFgm.class),
    SearchVideo(SearchVideoFgm.class),
    SearchPeople(SearchPeopleFgm.class),

    Interested(InterestedFgm.class),
    ;


    @NotNull
    private final Class value;

    @NotNull
    public final Class getValue() {
        return this.value;
    }

    PageEnum(@NotNull Class value) {
        this.value = value;
    }
}
