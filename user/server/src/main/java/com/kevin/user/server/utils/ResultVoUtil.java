package com.kevin.user.server.utils;

import com.kevin.user.server.eunm.ResultEunm;
import com.kevin.user.server.vo.ResultVO;

public class ResultVoUtil {
    public static ResultVO success(Object obj){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData(obj);
        return resultVO;
    }

    public static ResultVO success(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        return resultVO;
    }

    public static ResultVO error(ResultEunm resultEunm){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEunm.getCode());
        resultVO.setMsg(resultEunm.getMsg());
        return resultVO;
    }
}
