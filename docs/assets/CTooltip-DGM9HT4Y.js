import{a as t,_ as Q,b as o,R as r,c as U,P as e,q as W,v as X}from"./index-vWEjdDY1.js";import{C as Y}from"./CConditionalPortal-Df4ayv3N.js";import{u as Z}from"./DefaultLayout-DmxUTzH6.js";import{u as $}from"./isRTL-DFOyR6EP.js";import{g as ee,e as ne}from"./getRTLPlacement-CIoHaSpQ.js";var x=t.forwardRef(function(n,F){var M=n.children,b=n.animation,j=b===void 0?!0:b,L=n.className,_=n.container,g=n.content,h=n.delay,s=h===void 0?0:h,P=n.fallbackPlacements,A=P===void 0?["top","right","bottom","left"]:P,y=n.offset,B=y===void 0?[0,6]:y,w=n.onHide,C=n.onShow,T=n.placement,I=T===void 0?"top":T,d=n.popperConfig,k=n.trigger,i=k===void 0?["hover","focus"]:k,m=n.visible,V=Q(n,["children","animation","className","container","content","delay","fallbackPlacements","offset","onHide","onShow","placement","popperConfig","trigger","visible"]),a=t.useRef(null),c=t.useRef(null),z=Z(F,a),E="tooltip".concat(t.useId()),R=t.useState(!1),l=R[0],N=R[1],S=t.useState(m),f=S[0],H=S[1],v=$(),D=v.initPopper,G=v.destroyPopper,J=v.updatePopper,O=typeof s=="number"?{show:s,hide:s}:s,q={modifiers:[{name:"arrow",options:{element:".tooltip-arrow"}},{name:"flip",options:{fallbackPlacements:A}},{name:"offset",options:{offset:B}}],placement:ee(I,c.current)},K=o(o({},q),typeof d=="function"?d(q):d);t.useEffect(function(){if(m){u();return}p()},[m]),t.useEffect(function(){if(l&&c.current&&a.current){D(c.current,a.current,K),setTimeout(function(){H(!0)},O.show);return}!l&&c.current&&a.current&&G()},[l]),t.useEffect(function(){!f&&c.current&&a.current&&ne(function(){N(!1)},a.current)},[f]);var u=function(){N(!0),C&&C()},p=function(){setTimeout(function(){H(!1),w&&w()},O.hide)};return t.useEffect(function(){J()},[g]),r.createElement(r.Fragment,null,r.cloneElement(M,o(o(o(o(o({},f&&{"aria-describedby":E}),{ref:c}),(i==="click"||i.includes("click"))&&{onClick:function(){return f?p():u()}}),(i==="focus"||i.includes("focus"))&&{onFocus:function(){return u()},onBlur:function(){return p()}}),(i==="hover"||i.includes("hover"))&&{onMouseEnter:function(){return u()},onMouseLeave:function(){return p()}})),r.createElement(Y,{container:_,portal:!0},l&&r.createElement("div",o({className:U("tooltip","bs-tooltip-auto",{fade:j,show:f},L),id:E,ref:z,role:"tooltip"},V),r.createElement("div",{className:"tooltip-arrow"}),r.createElement("div",{className:"tooltip-inner"},g))))});x.propTypes={animation:e.bool,children:e.node,container:e.any,content:e.oneOfType([e.string,e.node]),delay:e.oneOfType([e.number,e.shape({show:e.number.isRequired,hide:e.number.isRequired})]),fallbackPlacements:X,offset:e.any,onHide:e.func,onShow:e.func,placement:e.oneOf(["auto","top","right","bottom","left"]),popperConfig:e.oneOfType([e.func,e.object]),trigger:W,visible:e.bool};x.displayName="CTooltip";export{x as C};
