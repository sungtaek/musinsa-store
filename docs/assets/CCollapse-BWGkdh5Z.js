import{R as E,a as C,_ as W,b as m,c as O,P as d}from"./index-CZQsGjd-.js";import{_ as j,g as V,h as D,T as F,u as q}from"./DefaultLayout-BVUjKWgz.js";function N(){return N=Object.assign?Object.assign.bind():function(e){for(var a=1;a<arguments.length;a++){var l=arguments[a];for(var s in l)({}).hasOwnProperty.call(l,s)&&(e[s]=l[s])}return e},N.apply(null,arguments)}function B(e,a){return e.classList?!!a&&e.classList.contains(a):(" "+(e.className.baseVal||e.className)+" ").indexOf(" "+a+" ")!==-1}function G(e,a){e.classList?e.classList.add(a):B(e,a)||(typeof e.className=="string"?e.className=e.className+" "+a:e.setAttribute("class",(e.className&&e.className.baseVal||"")+" "+a))}function w(e,a){return e.replace(new RegExp("(^|\\s)"+a+"(?:\\s|$)","g"),"$1").replace(/\s+/g," ").replace(/^\s*|\s*$/g,"")}function I(e,a){e.classList?e.classList.remove(a):typeof e.className=="string"?e.className=w(e.className,a):e.setAttribute("class",w(e.className&&e.className.baseVal||"",a))}var J=function(a,l){return a&&l&&l.split(" ").forEach(function(s){return G(a,s)})},x=function(a,l){return a&&l&&l.split(" ").forEach(function(s){return I(a,s)})},A=function(e){j(a,e);function a(){for(var s,i=arguments.length,c=new Array(i),o=0;o<i;o++)c[o]=arguments[o];return s=e.call.apply(e,[this].concat(c))||this,s.appliedClasses={appear:{},enter:{},exit:{}},s.onEnter=function(n,t){var r=s.resolveArguments(n,t),p=r[0],u=r[1];s.removeClasses(p,"exit"),s.addClass(p,u?"appear":"enter","base"),s.props.onEnter&&s.props.onEnter(n,t)},s.onEntering=function(n,t){var r=s.resolveArguments(n,t),p=r[0],u=r[1],f=u?"appear":"enter";s.addClass(p,f,"active"),s.props.onEntering&&s.props.onEntering(n,t)},s.onEntered=function(n,t){var r=s.resolveArguments(n,t),p=r[0],u=r[1],f=u?"appear":"enter";s.removeClasses(p,f),s.addClass(p,f,"done"),s.props.onEntered&&s.props.onEntered(n,t)},s.onExit=function(n){var t=s.resolveArguments(n),r=t[0];s.removeClasses(r,"appear"),s.removeClasses(r,"enter"),s.addClass(r,"exit","base"),s.props.onExit&&s.props.onExit(n)},s.onExiting=function(n){var t=s.resolveArguments(n),r=t[0];s.addClass(r,"exit","active"),s.props.onExiting&&s.props.onExiting(n)},s.onExited=function(n){var t=s.resolveArguments(n),r=t[0];s.removeClasses(r,"exit"),s.addClass(r,"exit","done"),s.props.onExited&&s.props.onExited(n)},s.resolveArguments=function(n,t){return s.props.nodeRef?[s.props.nodeRef.current,n]:[n,t]},s.getClassNames=function(n){var t=s.props.classNames,r=typeof t=="string",p=r&&t?t+"-":"",u=r?""+p+n:t[n],f=r?u+"-active":t[n+"Active"],v=r?u+"-done":t[n+"Done"];return{baseClassName:u,activeClassName:f,doneClassName:v}},s}var l=a.prototype;return l.addClass=function(i,c,o){var n=this.getClassNames(c)[o+"ClassName"],t=this.getClassNames("enter"),r=t.doneClassName;c==="appear"&&o==="done"&&r&&(n+=" "+r),o==="active"&&i&&V(i),n&&(this.appliedClasses[c][o]=n,J(i,n))},l.removeClasses=function(i,c){var o=this.appliedClasses[c],n=o.base,t=o.active,r=o.done;this.appliedClasses[c]={},n&&x(i,n),t&&x(i,t),r&&x(i,r)},l.render=function(){var i=this.props;i.classNames;var c=D(i,["classNames"]);return E.createElement(F,N({},c,{onEnter:this.onEnter,onEntered:this.onEntered,onEntering:this.onEntering,onExit:this.onExit,onExiting:this.onExiting,onExited:this.onExited}))},a}(E.Component);A.defaultProps={classNames:""};A.propTypes={};var S=C.forwardRef(function(e,a){var l=e.children,s=e.className,i=e.horizontal,c=e.onHide,o=e.onShow,n=e.visible,t=W(e,["children","className","horizontal","onHide","onShow","visible"]),r=C.useRef(null),p=q(a,r),u=C.useState(),f=u[0],v=u[1],$=C.useState(),b=$[0],g=$[1],_=function(){if(o&&o(),i){r.current&&g(r.current.scrollWidth);return}r.current&&v(r.current.scrollHeight)},R=function(){if(i){g(0);return}v(0)},H=function(){if(i){r.current&&g(r.current.scrollWidth);return}r.current&&v(r.current.scrollHeight)},L=function(){if(c&&c(),i){g(0);return}v(0)},T=function(){if(i){g(0);return}v(0)};return E.createElement(A,{in:n,nodeRef:r,onEntering:_,onEntered:R,onExit:H,onExiting:L,onExited:T,timeout:350},function(h){var z=f===0?null:{height:f},P=b===0?null:{width:b};return E.createElement("div",m({className:O(s,{"collapse-horizontal":i,collapsing:h==="entering"||h==="exiting","collapse show":h==="entered",collapse:h==="exited"}),style:m(m({},z),P)},t,{ref:p}),l)})});S.propTypes={children:d.node,className:d.string,horizontal:d.bool,onHide:d.func,onShow:d.func,visible:d.bool};S.displayName="CCollapse";export{S as C};
