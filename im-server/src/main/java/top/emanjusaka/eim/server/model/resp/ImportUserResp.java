package top.emanjusaka.eim.server.model.resp;

import lombok.Data;

import java.util.Set;

/**
 * @author: Chackylee
 * @description:
 **/
@Data
public class ImportUserResp {
    private Set<String> successId;

    private Set<String> errorId;
}
