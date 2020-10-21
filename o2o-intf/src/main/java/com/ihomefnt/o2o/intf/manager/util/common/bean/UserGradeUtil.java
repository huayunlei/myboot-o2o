package com.ihomefnt.o2o.intf.manager.util.common.bean;

public final class UserGradeUtil {

    public static boolean validateSingleGrade(int comparedGrade, int referenceGrade) {
        int resultGrade = comparedGrade & referenceGrade;
        boolean possessBool = resultGrade == referenceGrade;
        return possessBool;
    }
}
