import{a as o,_ as p,R as t,b as m,c as d,P as n}from"./index-va5UnrO5.js";import{C as v}from"./cil-monitor-Ecr67Ieb.js";var g=o.forwardRef(function(e,c){var a,r=e.children,l=e.align,s=e.className,i=e.size,f=p(e,["children","align","className","size"]);return t.createElement("nav",m({ref:c},f),t.createElement("ul",{className:d("pagination",(a={},a["justify-content-".concat(l)]=l,a["pagination-".concat(i)]=i,a),s)},r))});g.propTypes={align:n.oneOf(["start","center","end"]),children:n.node,className:n.string,size:n.oneOf(["sm","lg"])};g.displayName="CPagination";var N=o.forwardRef(function(e,c){var a=e.children,r=e.as,l=e.className,s=p(e,["children","as","className"]),i=r??(s.active?"span":"a");return t.createElement("li",m({className:d("page-item",{active:s.active,disabled:s.disabled},l)},s.active&&{"aria-current":"page"}),i==="a"?t.createElement(v,m({className:"page-link",as:i},s,{ref:c}),a):t.createElement(i,{className:"page-link",ref:c},a))});N.propTypes={as:n.elementType,children:n.node,className:n.string};N.displayName="CPaginationItem";export{g as C,N as a};
