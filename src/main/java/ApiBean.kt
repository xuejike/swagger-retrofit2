package test
data class ApiBean(var tags:List<Tags>?=null,
                   var paths:Map<String,Api>?=null)
data class Tags(var name:String?=null,var description:String?=null);
data class Api(var post:ApiInfo?=null,var get:ApiInfo?=null)
data class ApiInfo(var tags:List<String>?=null,
                   var summary:String?=null,
                   var parameters:List<Parameter>?=null);
data class Parameter(var name:String? = null,var type:String? = null,var description:String? = null)