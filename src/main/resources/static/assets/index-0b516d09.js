
/**
 * 由 Fantastic-admin 提供技术支持
 * Powered by Fantastic-admin
 * Gitee  https://gitee.com/hooray/fantastic-admin
 * Github https://github.com/hooray/fantastic-admin
 */
  
import{_ as M}from"./index-d253de1f.js";import{y as x,d as h,k as B,z as L,A,B as D,C as T,D as _,x as V,E as i,F as z,a as b,o as a,c as H,b as n,f as s,G as f,e as t,H as I,I as r,J as v,g,T as K,K as N,w as P,L as $,v as E,n as R}from"./index-fcfafe90.js";import F from"./link-af215666.js";import G from"./index-abdd555a.js";import J from"./index-056ca77b.js";import U from"./index-86bfea39.js";import j from"./index-74347da7.js";import q from"./index-1778ab96.js";import O from"./index-f438fea9.js";import{u as Q}from"./useMenu-bebd9d3a.js";import"./index-22576a47.js";import"./index-0f628d05.js";import"./index-1eea3908.js";function W(){function m(){x.push({name:"reload"})}return{reload:m}}const X={class:"layout"},Y={id:"app-main"},Z={class:"wrapper"},ee={class:"main"},te=h({name:"Layout"}),oe=h({...te,setup(m){const u=B(),e=L(),k=A(),l=D(),d=T(()=>!!u.meta.link);return _(()=>e.settings.menu.subMenuCollapse,o=>{e.mode==="mobile"&&(o?document.body.classList.remove("hidden"):document.body.classList.add("hidden"))}),_(()=>u.path,()=>{e.mode==="mobile"&&e.$patch(o=>{o.settings.menu.subMenuCollapse=!0})}),V(()=>{i("f5",o=>{e.settings.toolbar.enablePageReload&&(o.preventDefault(),W().reload())}),i("alt+`",o=>{e.settings.menu.enableHotkeys&&(o.preventDefault(),Q().switchTo(l.actived+1<l.allMenus.length?l.actived+1:0))})}),z(()=>{i.unbind("f5"),i.unbind("alt+`")}),(o,c)=>{const y=b("router-view"),S=M,C=b("el-backtop");return a(),H("div",X,[n("div",Y,[s(G),n("div",Z,[n("div",{class:f(["sidebar-container",{show:t(e).mode==="mobile"&&!t(e).settings.menu.subMenuCollapse}])},[s(J),s(U)],2),n("div",{class:f(["sidebar-mask",{show:t(e).mode==="mobile"&&!t(e).settings.menu.subMenuCollapse}]),onClick:c[0]||(c[0]=p=>t(e).toggleSidebarCollapse())},null,2),n("div",{class:"main-container",style:I({"padding-bottom":o.$route.meta.paddingBottom})},[t(e).settings.menu.menuMode==="head"&&!t(e).settings.menu.enableSubMenuCollapseButton&&!t(e).settings.breadcrumb.enable?v("",!0):(a(),r(j,{key:0})),n("div",ee,[s(y,null,{default:g(({Component:p,route:w})=>[s(K,{name:"main",mode:"out-in",appear:""},{default:g(()=>[(a(),r(N,{include:t(k).list},[P((a(),r($(p),{key:w.fullPath})),[[E,!t(d)]])],1032,["include"]))]),_:2},1024)]),_:1}),t(d)?(a(),r(F,{key:0})):v("",!0)]),s(S)],4)]),s(C,{right:20,bottom:20,title:"回到顶部"})]),s(q),s(O)])}}});const fe=R(oe,[["__scopeId","data-v-df83361b"]]);export{fe as default};
//# sourceMappingURL=index-0b516d09.js.map