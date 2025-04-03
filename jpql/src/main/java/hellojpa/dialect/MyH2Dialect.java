package hellojpa.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MyH2Dialect extends H2Dialect {

/*  하이버네이트 5.x.x 버전까지
    public MyH2Dialect() {
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
*/
    /* 하이버네이트 6버전 이상 */
    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions
                .getFunctionRegistry()
                .register("group_concat",
                        new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
