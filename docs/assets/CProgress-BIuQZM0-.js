import{a as i,_ as v,R as t,b as o,c as N,P as r,d as P}from"./index-vWEjdDY1.js";var C=i.createContext({}),y=i.forwardRef(function(e,c){var a=e.children,d=e.className,l=v(e,["children","className"]);return t.createElement("div",o({className:N("progress-stacked",d),ref:c},l),t.createElement(C.Provider,{value:{stacked:!0}},a))});y.propTypes={children:r.node,className:r.string};y.displayName="CProgressStacked";var f=i.forwardRef(function(e,c){var a,d=e.children,l=e.animated,p=e.className,h=e.color,s=e.value,u=s===void 0?0:s,m=e.variant,g=v(e,["children","animated","className","color","value","variant"]),n=i.useContext(C).stacked;return t.createElement("div",o({className:N("progress-bar",(a={},a["bg-".concat(h)]=h,a["progress-bar-".concat(m)]=m,a["progress-bar-animated"]=l,a),p)},!n&&{style:{width:"".concat(u,"%")}},g,{ref:c}),d)});f.propTypes={animated:r.bool,children:r.node,className:r.string,color:P,value:r.number,variant:r.oneOf(["striped"])};f.displayName="CProgressBar";var b=i.forwardRef(function(e,c){var a=e.children,d=e.className,l=e.height,p=e.progressBarClassName,h=e.thin,s=e.value,u=e.white,m=v(e,["children","className","height","progressBarClassName","thin","value","white"]),g=i.useContext(C).stacked;return t.createElement("div",o({className:N("progress",{"progress-thin":h,"progress-white":u},d)},s!==void 0&&{role:"progressbar","aria-valuenow":s,"aria-valuemin":0,"aria-valuemax":100},{style:o(o({},l?{height:"".concat(l,"px")}:{}),g?{width:"".concat(s,"%")}:{}),ref:c}),t.Children.toArray(a).some(function(n){return n.type&&n.type.displayName==="CProgressBar"})?t.Children.map(a,function(n){if(t.isValidElement(n)&&n.type.displayName==="CProgressBar")return t.cloneElement(n,o(o({},s&&{value:s}),m))}):t.createElement(f,o({},p&&{className:p},{value:s},m),a))});b.propTypes={children:r.node,className:r.string,height:r.number,progressBarClassName:r.string,thin:r.bool,value:r.number,white:r.bool};b.displayName="CProgress";export{b as C,f as a};
