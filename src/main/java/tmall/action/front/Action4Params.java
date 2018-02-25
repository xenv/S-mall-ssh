package tmall.action.front;

import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.User;

import java.math.BigDecimal;
import java.util.List;


public class Action4Params extends Action4Service {
    protected String msg;
    protected String refer;
    protected String sort;
    protected String keyword;
    protected int num;

    protected int totalNum = 0;
    protected BigDecimal sum = new BigDecimal(0);

    protected List<Integer> ciids;

    public List<Integer> getCiids() {
        return ciids;
    }

    public void setCiids(List<Integer> ciids) {
        this.ciids = ciids;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String[] handleSort(){

        sort = sort==null?"":sort;
        String order = "desc";
        String column = "";
        switch(sort){
            case "date":
                column = "createDate";
                break;
            case "comment":
                column = "commentCount";
                break;
            case "saleCount":
                column = "saleCount";
                break;
            case "price":
                column = "nowPrice";
                order = "asc";
                break;
            case "priceInverse":
                column = "nowPrice";
                break;
            default:
                column = "commentCount";
                break;
        }
        return new String[]{order,column};
    }

}
