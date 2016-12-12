package com.hongluomeng.controller;

import com.hongluomeng.common.Private;
import com.hongluomeng.service.SmsService;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.MsgController;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

public class WeixinController extends MsgController {

    private SmsService smsService = new SmsService();

    @Override
    public ApiConfig getApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setAppId(Private.WEIXIN_MP_APP_ID);
        apiConfig.setAppSecret(Private.WEIXIN_MP_APP_SECRET);
        apiConfig.setToken(Private.WEIXIN_MP_TOKEN);
        apiConfig.setEncryptMessage(false);
        apiConfig.setEncodingAesKey(Private.WEIXIN_MP_ENCODING_AESKEY);

        return apiConfig;
    }

    @Override
    protected void processInTextMsg(InTextMsg inTextMsg) {
        String text = inTextMsg.getContent().trim();

        if (text.equalsIgnoreCase("M")) {
            String sms_code = smsService.saveForWeixin();

            OutTextMsg outMsg = new OutTextMsg(inTextMsg);
            outMsg.setContent("验证码: " + sms_code);

            render(outMsg);
        }
    }

    @Override
    protected void processInImageMsg(InImageMsg inImageMsg) {

    }

    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {

    }

    @Override
    protected void processInVideoMsg(InVideoMsg inVideoMsg) {

    }

    @Override
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {

    }

    @Override
    protected void processInLocationMsg(InLocationMsg inLocationMsg) {

    }

    @Override
    protected void processInLinkMsg(InLinkMsg inLinkMsg) {

    }

    @Override
    protected void processInCustomEvent(InCustomEvent inCustomEvent) {

    }

    @Override
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
        outMsg.setContent("感谢关注红萝梦RedEmpire");
        // 如果为取消关注事件，将无法接收到传回的信息
        render(outMsg);
    }

    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
        OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
        outMsg.setContent("processInQrCodeEvent() 方法测试成功");
        render(outMsg);
    }

    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {
        OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
        outMsg.setContent("processInLocationEvent() 方法测试成功");
        render(outMsg);
    }

    @Override
    protected void processInMassEvent(InMassEvent inMassEvent) {

    }

    @Override
    protected void processInMenuEvent(InMenuEvent inMenuEvent) {
        OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
        outMsg.setContent("processInMenuEvent() 方法测试成功");
        render(outMsg);
    }

    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
        OutTextMsg outMsg = new OutTextMsg(inSpeechRecognitionResults);
        outMsg.setContent("processInSpeechRecognitionResults() 方法测试成功");
        render(outMsg);
    }

    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {

    }

    @Override
    protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {

    }

    @Override
    protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {

    }

    @Override
    protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {

    }

    @Override
    protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {

    }

    @Override
    protected void processInWifiEvent(InWifiEvent inWifiEvent) {

    }

    @Override
    protected void processInUserViewCardEvent(InUserViewCardEvent inUserViewCardEvent) {

    }

    @Override
    protected void processInSubmitMemberCardEvent(InSubmitMemberCardEvent inSubmitMemberCardEvent) {

    }

    @Override
    protected void processInUpdateMemberCardEvent(InUpdateMemberCardEvent inUpdateMemberCardEvent) {

    }

    @Override
    protected void processInUserPayFromCardEvent(InUserPayFromCardEvent inUserPayFromCardEvent) {

    }

    @Override
    protected void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent) {

    }

    @Override
    protected void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent) {

    }

    @Override
    protected void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg) {

    }
}
