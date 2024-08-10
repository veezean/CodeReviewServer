
/**
 * 由 Fantastic-admin 提供技术支持
 * Powered by Fantastic-admin
 * Gitee  https://gitee.com/hooray/fantastic-admin
 * Github https://github.com/hooray/fantastic-admin
 */
  
import{d as h,m as y,z as C,u as S,a5 as B,a as t,o as p,c as N,b as n,e as a,X as g,f as e,g as o,i as s,I as V,t as f,J as z,_ as T,n as I}from"./index-04c83304.js";const $={class:"tools"},D={class:"buttons"},E={class:"user-wrapper"},F={class:"user-wrapper"},J=h({name:"Tools"}),R=h({...J,setup(U){const l=y(),c=C(),r=S();B();function v(d){switch(d){case"home":l.push({name:"home"});break;case"setting":l.push({name:"personalSetting"});break;case"hotkeys":g.emit("global-hotkeys-intro-toggle");break;case"logout":r.logout().then(()=>{l.push({name:"login"})});break}}return(d,u)=>{const w=t("el-tag"),m=T,i=t("el-icon"),k=t("el-avatar"),_=t("el-dropdown-item"),b=t("el-dropdown-menu"),x=t("el-dropdown");return p(),N("div",$,[n("div",D,[n("span",{class:"user-container",onClick:u[0]||(u[0]=X=>a(g).emit("global-version-help"))},[n("div",E,[e(w,{type:"danger"},{default:o(()=>[s("版本&帮助")]),_:1})])])]),e(x,{class:"user-container",size:"default",onCommand:v},{dropdown:o(()=>[e(b,{class:"user-dropdown"},{default:o(()=>[a(c).settings.home.enable?(p(),V(_,{key:0,command:"home"},{default:o(()=>[s(f(a(c).settings.home.title),1)]),_:1})):z("",!0),e(_,{command:"setting"},{default:o(()=>[s(" 个人设置 ")]),_:1}),e(_,{divided:"",command:"logout"},{default:o(()=>[s(" 退出登录 ")]),_:1})]),_:1})]),default:o(()=>[n("div",F,[e(k,{size:"small"},{default:o(()=>[e(i,null,{default:o(()=>[e(m,{name:"ep:user-filled"})]),_:1})]),_:1}),s(" "+f(a(r).name)+" ",1),e(i,null,{default:o(()=>[e(m,{name:"ep:caret-bottom"})]),_:1})])]),_:1})])}}});const q=I(R,[["__scopeId","data-v-5037878a"]]);export{q as default};
//# sourceMappingURL=index-973bc8e4.js.map
