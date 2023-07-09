
/**
 * 由 Fantastic-admin 提供技术支持
 * Powered by Fantastic-admin
 * Gitee  https://gitee.com/hooray/fantastic-admin
 * Github https://github.com/hooray/fantastic-admin
 */
  
function o(i){return{all:i=i||new Map,on:function(e,n){var t=i.get(e);t?t.push(n):i.set(e,[n])},off:function(e,n){var t=i.get(e);t&&(n?t.splice(t.indexOf(n)>>>0,1):i.set(e,[]))},emit:function(e,n){var t=i.get(e);t&&t.slice().map(function(c){c(n)}),(t=i.get("*"))&&t.slice().map(function(c){c(e,n)})}}}const f=o();export{f as e};
//# sourceMappingURL=eventBus-d154090d.js.map
