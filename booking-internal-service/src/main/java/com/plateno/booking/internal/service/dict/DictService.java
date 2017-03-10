package com.plateno.booking.internal.service.dict;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.DictMapper;
import com.plateno.booking.internal.base.pojo.Dict;
import com.plateno.booking.internal.base.pojo.DictExample;

@Service
public class DictService {
    
    @Autowired
    private DictMapper dictMapper;
    
    public Dict findDictByKey(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        DictExample example = new DictExample();
        example.createCriteria().andOrderKeyEqualTo(key);
        List<Dict> list = dictMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list))
            return null;
        return list.get(0);
    }
    
    
}
