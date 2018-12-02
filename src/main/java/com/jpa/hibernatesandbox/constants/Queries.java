package com.jpa.hibernatesandbox.constants;

public final class Queries {

    public static final String GET_PARENT_BY_PARENT_ID =
            "SELECT p FROM Parent p "
//            + "LEFT OUTER JOIN FETCH p.childList cl "
//            + "LEFT OUTER JOIN FETCH p.manyObjectList mol "
//            + "LEFT OUTER JOIN FETCH p.assocTableEntryList atl "
//            + "LEFT OUTER JOIN FETCH atl.stepChild "
            + "WHERE p.parentId = :parentId";
}
