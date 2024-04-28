
/**
 * 由 Fantastic-admin 提供技术支持
 * Powered by Fantastic-admin
 * Gitee  https://gitee.com/hooray/fantastic-admin
 * Github https://github.com/hooray/fantastic-admin
 */
  
import{d as _,z as S,B as h,r as M,a as v,e,o as t,c as o,f as l,G as u,g as c,a2 as C,M as m,U as k,I as w,J as p,n as x}from"./index-fcfafe90.js";import y from"./index-22576a47.js";import B from"./index-1eea3908.js";const q=_({name:"SubSidebar"}),I=_({...q,setup(T){const s=S(),i=h(),r=M(0);function g(n){r.value=n.target.scrollTop}return(n,z)=>{const b=v("el-menu");return["side","head","single"].includes(e(s).settings.menu.menuMode)||e(s).mode==="mobile"?(t(),o("div",{key:0,class:u(["sub-sidebar-container",{"is-collapse":e(s).mode==="pc"&&e(s).settings.menu.subMenuCollapse}]),onScroll:g},[l(y,{"show-logo":e(s).settings.menu.menuMode==="single",class:u(["sidebar-logo",{"sidebar-logo-bg":e(s).settings.menu.menuMode==="single",shadow:e(r)}])},null,8,["show-logo","class"]),l(b,{"unique-opened":e(s).settings.menu.subMenuUniqueOpened,"default-openeds":e(i).defaultOpenedPaths,"default-active":n.$route.meta.activeMenu||n.$route.path,collapse:e(s).mode==="pc"&&e(s).settings.menu.subMenuCollapse,"collapse-transition":!1,class:u({"is-collapse-without-logo":e(s).settings.menu.menuMode!=="single"&&e(s).settings.menu.subMenuCollapse})},{default:c(()=>[l(C,{name:"sub-sidebar"},{default:c(()=>[(t(!0),o(m,null,k(e(i).sidebarMenus,(a,f)=>{var d;return t(),o(m,null,[((d=a.meta)==null?void 0:d.sidebar)!==!1?(t(),w(B,{key:a.path||f,item:a,"base-path":a.path},null,8,["item","base-path"])):p("",!0)],64)}),256))]),_:1})]),_:1},8,["unique-opened","default-openeds","default-active","collapse","class"])],34)):p("",!0)}}});const O=x(I,[["__scopeId","data-v-c751a211"]]);export{O as default};
//# sourceMappingURL=index-86bfea39.js.map
