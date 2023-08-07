package top.emanjusaka.eim.service.user.model.resp;

import lombok.Data;

import java.util.List;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class ImportUserResp {

    private List<String> successId;

    private List<String> errorId;

}
