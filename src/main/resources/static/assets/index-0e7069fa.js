
/**
 * 由 Fantastic-admin 提供技术支持
 * Powered by Fantastic-admin
 * Gitee  https://gitee.com/hooray/fantastic-admin
 * Github https://github.com/hooray/fantastic-admin
 */
  
import{_ as te}from"./index.vue_vue_type_script_setup_true_name_SvgIcon_lang-dfa84782.js";import{b as z,w as x}from"./runtime-dom.esm-bundler-1ceaee55.js";import{h}from"./hotkeys.esm-d20e5801.js";import{e as I}from"./eventBus-d154090d.js";import{u as se}from"./settings-58b03ff3.js";import{b8 as ae,b7 as le,ab as F}from"./user-2b228075.js";import{u as ne}from"./vue-router-8228f7a0.js";import{j as G,r as y,a4 as oe,a as re,w as O,o as ue,a3 as M,k as b,l as R,m as c,E as n,y as r,u as p,f as ie,C as k,D as B,B as q,F as A,Y as J,q as Y,x as ce}from"./runtime-core.esm-bundler-a63aef9e.js";import{_ as fe}from"./_plugin-vue_export-helper-c27b6911.js";import"./index-c228ae8f.js";import"./index-e71fcdfa.js";import"./index-74c80a46.js";import"./route-block-83d24a4e.js";const de={class:"container"},pe={key:0,class:"tips"},ve={class:"tip"},he={class:"tip"},_e={class:"tip"},me={class:"tip"},ge=["onClick","onMouseover"],ye={class:"info"},be={class:"title"},ke={class:"breadcrumb"},Se={class:"path"},we=G({name:"Search"}),Ce=G({...we,setup(Re){const Q=ne(),_=se(),W=ae(),X=le(),m=y(!1),f=y(""),E=y([]),u=y(-1),$=y(),S=y(),w=y([]),Z=s=>w.value.push(s);oe(()=>{w.value=[]});const C=re(()=>{let s=[];return s=E.value.filter(e=>{let l=!1;return e.title&&(typeof e.title=="function"?e.title().includes(f.value)&&(l=!0):e.title.includes(f.value)&&(l=!0)),e.path.includes(f.value)&&(l=!0),e.breadcrumb.some(a=>{let t=!1;return a&&(typeof a=="function"?a().includes(f.value)&&(t=!0):a.includes(f.value)&&(t=!0)),t})&&(l=!0),l}),s});O(()=>m.value,s=>{s?(document.body.classList.add("hidden"),S.value.scrollTop=0,h("up",L),h("down",V),h("enter",D),setTimeout(()=>{$.value.focus()},500)):(document.body.classList.remove("hidden"),h.unbind("up",L),h.unbind("down",V),h.unbind("enter",D),setTimeout(()=>{f.value="",u.value=-1},500))}),O(()=>C.value,()=>{u.value=-1,w.value=[],H()}),ue(()=>{I.on("global-search-toggle",()=>{m.value=!m.value}),h("alt+s",s=>{_.settings.navSearch.enable&&_.settings.navSearch.enableHotkeys&&(s.preventDefault(),m.value=!0)}),h("esc",s=>{_.settings.navSearch.enable&&_.settings.navSearch.enableHotkeys&&(s.preventDefault(),m.value=!1)}),_.settings.app.routeBaseOn!=="filesystem"?W.routes.forEach(s=>{s.children&&N(s.children)}):X.menus.forEach(s=>{U(s.children)})});function P(s){var l;let e=!0;return(l=s.children)!=null&&l.every(a=>{var t;return((t=a.meta)==null?void 0:t.sidebar)===!1})&&(e=!1),e}function N(s,e,l,a){s.forEach(t=>{var i,o,v,d,g,j,K;if(((i=t.meta)==null?void 0:i.sidebar)!==!1){const T=F(a)||[];t.children&&P(t)?(T.push((o=t.meta)==null?void 0:o.title),N(t.children,e?[e,t.path].join("/"):t.path,((v=t.meta)==null?void 0:v.icon)??l,T)):(T.push((d=t.meta)==null?void 0:d.title),E.value.push({path:e?[e,t.path].join("/"):t.path,icon:((g=t.meta)==null?void 0:g.icon)??l,title:(j=t.meta)==null?void 0:j.title,link:(K=t.meta)==null?void 0:K.link,breadcrumb:T}))}})}function U(s,e,l){s.forEach(a=>{var i,o,v,d,g;const t=F(l)||[];a.children&&a.children.length>0?(t.push((i=a.meta)==null?void 0:i.title),U(a.children,((o=a.meta)==null?void 0:o.icon)??e,t)):(t.push((v=a.meta)==null?void 0:v.title),E.value.push({icon:((d=a.meta)==null?void 0:d.icon)??e,title:(g=a.meta)==null?void 0:g.title,path:a.path,breadcrumb:t}))})}function L(){C.value.length&&(u.value-=1,u.value<0&&(u.value=C.value.length-1),H())}function V(){C.value.length&&(u.value+=1,u.value>C.value.length-1&&(u.value=0),H())}function D(){u.value!==-1&&w.value[u.value].click()}function H(){let s=0;if(u.value!==-1){s=S.value.scrollTop;const e=w.value[u.value].offsetTop,l=w.value[u.value].clientHeight,a=S.value.scrollTop,t=S.value.clientHeight;e+l>a+t?s=e+l-t:e<=a&&(s=e)}S.value.scrollTo({top:s})}function ee(s,e){e?window.open(e,"_blank"):Q.push(s)}return(s,e)=>{const l=te,a=M("el-icon"),t=M("el-input"),i=M("el-tag");return b(),R("div",{id:"search",class:Y({searching:p(m)}),onClick:e[3]||(e[3]=o=>p(m)&&p(I).emit("global-search-toggle"))},[c("div",de,[c("div",{class:"search-box",onClick:e[2]||(e[2]=z(()=>{},["stop"]))},[n(t,{ref_key:"searchInputRef",ref:$,modelValue:p(f),"onUpdate:modelValue":e[0]||(e[0]=o=>ie(f)?f.value=o:null),placeholder:"搜索页面，支持标题、URL模糊查询",clearable:"",onKeydown:[e[1]||(e[1]=x(o=>p(I).emit("global-search-toggle"),["esc"])),x(z(L,["prevent"]),["up"]),x(z(V,["prevent"]),["down"]),x(z(D,["prevent"]),["enter"])]},{prefix:r(()=>[n(a,null,{default:r(()=>[n(l,{name:"ep:search"})]),_:1})]),_:1},8,["modelValue","onKeydown"]),p(_).mode==="pc"?(b(),R("div",pe,[c("div",ve,[n(i,{type:"info",size:"large"},{default:r(()=>[k(B(p(_).os==="mac"?"⌥":"Alt")+" + S ",1)]),_:1}),n(i,{type:"info",size:"large"},{default:r(()=>[k(" 唤醒搜索面板 ")]),_:1})]),c("div",he,[n(i,{type:"info",size:"large"},{default:r(()=>[n(a,null,{default:r(()=>[n(l,{name:"search-up"})]),_:1})]),_:1}),n(i,{type:"info",size:"large"},{default:r(()=>[n(a,null,{default:r(()=>[n(l,{name:"search-down"})]),_:1})]),_:1}),n(i,{type:"info",size:"large"},{default:r(()=>[k(" 切换搜索结果 ")]),_:1})]),c("div",_e,[n(i,{type:"info",size:"large"},{default:r(()=>[n(a,null,{default:r(()=>[n(l,{name:"search-enter"})]),_:1})]),_:1}),n(i,{type:"info",size:"large"},{default:r(()=>[k(" 访问页面 ")]),_:1})]),c("div",me,[n(i,{type:"info",size:"large"},{default:r(()=>[k(" ESC ")]),_:1}),n(i,{type:"info",size:"large"},{default:r(()=>[k(" 退出 ")]),_:1})])])):q("",!0)]),c("div",{ref_key:"searchResultRef",ref:S,class:"result"},[(b(!0),R(A,null,J(p(C),(o,v)=>(b(),R("a",{key:o.path,ref_for:!0,ref:Z,class:Y(["item",{actived:v===p(u)}]),onClick:d=>ee(o.path,o.link),onMouseover:d=>u.value=v},[n(a,{class:"icon"},{default:r(()=>[o.icon?(b(),ce(l,{key:0,name:o.icon},null,8,["name"])):q("",!0)]),_:2},1024),c("div",ye,[c("div",be,B(o.title??"[ 无标题 ]"),1),c("div",ke,[(b(!0),R(A,null,J(o.breadcrumb,(d,g)=>(b(),R("span",{key:g},[k(B(d??"[ 无标题 ]")+" ",1),n(a,null,{default:r(()=>[n(l,{name:"ep:arrow-right"})]),_:1})]))),128))]),c("div",Se,B(o.path),1)])],42,ge))),128))],512)])],2)}}});const Ue=fe(Ce,[["__scopeId","data-v-7ddd62c9"]]);export{Ue as default};
//# sourceMappingURL=index-0e7069fa.js.map