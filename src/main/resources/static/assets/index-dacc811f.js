
/**
 * 由 Fantastic-admin 提供技术支持
 * Powered by Fantastic-admin
 * Gitee  https://gitee.com/hooray/fantastic-admin
 * Github https://github.com/hooray/fantastic-admin
 */
  
import{d as i,r as v,D as h,a as c,o as l,c as _,P as x,f as t,g as o,e as r,i as y,t as B,J as C,_ as k,n as w}from"./index-fcfafe90.js";const b={class:"search-container"},N={key:0,class:"more"},S=i({name:"SearchBar"}),V=i({...S,props:{showMore:{type:Boolean,default:!1},unfold:{type:Boolean,default:!1}},emits:["toggle"],setup(n,{emit:u}){const a=n,d=u,e=v(!a.unfold);h(()=>a.unfold,()=>s(),{immediate:!0});function s(){e.value=!e.value,d("toggle",e.value)}return(m,D)=>{const p=k,f=c("el-icon"),g=c("el-button");return l(),_("div",b,[x(m.$slots,"default",{},void 0,!0),n.showMore?(l(),_("div",N,[t(g,{text:"",size:"small",onClick:s},{icon:o(()=>[t(f,null,{default:o(()=>[t(p,{name:r(e)?"ep:caret-top":"ep:caret-bottom"},null,8,["name"])]),_:1})]),default:o(()=>[y(" "+B(r(e)?"收起":"展开"),1)]),_:1})])):C("",!0)])}}});const $=w(V,[["__scopeId","data-v-9a63a098"]]);export{$ as _};
//# sourceMappingURL=index-dacc811f.js.map
