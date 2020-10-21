package com.ihomefnt.o2o.service.proxy.log;

import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.log.dto.VisitLogDto;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.user.LogProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class LogProxyImpl implements LogProxy {
	
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public void addLog(Map<String, Object> params) {
		List<TaskAction<?>> taskActions = new ArrayList<>();
        // 添加日志记录任务
        taskActions.add(new TaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                try {
                    strongSercviceCaller.post(WcmWebServiceNameConstants.ADD_LOG_FOR_APP, params,
                            HttpBaseResponse.class);
            		
                } catch (Exception e) {

                }
                return 1;
            }
        });
        // 执行任务
        Executor.getInvokeOuterServiceFactory().asyncExecuteTask(taskActions);

		return;
	}

	@Override
	public VisitLogDto queryVisitLogByCondition(Map<String, Object> params) {
		HttpBaseResponse<VisitLogDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_VISIT_LOG_BY_CONDITION, params,
				new TypeReference<HttpBaseResponse<VisitLogDto>>(){});
		
		if(response != null){
			return response.getObj();
		}
		return null;
	}

}
