import{a as n,_ as R,R as l,b as u,c as A,P as t,d as W,w as q,j as e}from"./index-vWEjdDY1.js";import{u as X,T as Z,l as D,a as $,b as x}from"./DefaultLayout-DmxUTzH6.js";import"./cil-monitor-B3oJwQN9.js";import{C as _,a as y}from"./CRow-Dc-PYaZc.js";import{C,a as N}from"./CCardBody-Dam5KOrS.js";import{C as T}from"./CCardHeader-Bzy8KaYG.js";import{C as ee}from"./CConditionalPortal-Df4ayv3N.js";import{C as V}from"./CButton-CgS9ie_u.js";var L=n.createContext({}),d=n.forwardRef(function(s,a){var i=s.children,o=s.animation,r=o===void 0?!0:o,c=s.autohide,U=c===void 0?!0:c,E=s.className,p=s.color,b=s.delay,v=b===void 0?5e3:b,m=s.index,w=s.innerKey,k=s.visible,B=k===void 0?!1:k,H=s.onClose,M=s.onShow,G=R(s,["children","animation","autohide","className","color","delay","index","innerKey","visible","onClose","onShow"]),O=n.useRef(),J=X(a,O),z=n.useState(!1),Y=z[0],K=z[1],S=n.useRef();n.useEffect(function(){K(B)},[B]);var Q={visible:Y,setVisible:K};n.useEffect(function(){return function(){return clearTimeout(S.current)}},[]),n.useEffect(function(){F()},[Y]);var F=function(){U&&(clearTimeout(S.current),S.current=window.setTimeout(function(){K(!1)},v))};return l.createElement(Z,{in:Y,nodeRef:O,onEnter:function(){return M&&M(m??null)},onExited:function(){return H&&H(m??null)},timeout:250,unmountOnExit:!0},function(P){var j;return l.createElement(L.Provider,{value:Q},l.createElement("div",u({className:A("toast",(j={fade:r},j["bg-".concat(p)]=p,j["border-0"]=p,j["show showing"]=P==="entering"||P==="exiting",j.show=P==="entered",j),E),"aria-live":"assertive","aria-atomic":"true",role:"alert",onMouseEnter:function(){return clearTimeout(S.current)},onMouseLeave:function(){return F()}},G,{key:w,ref:J}),i))})});d.propTypes={animation:t.bool,autohide:t.bool,children:t.node,className:t.string,color:W,delay:t.number,index:t.number,innerKey:t.oneOfType([t.number,t.string]),onClose:t.func,onShow:t.func,visible:t.bool};d.displayName="CToast";var h=n.forwardRef(function(s,a){var i=s.children,o=s.className,r=R(s,["children","className"]);return l.createElement("div",u({className:A("toast-body",o)},r,{ref:a}),i)});h.propTypes={children:t.node,className:t.string};h.displayName="CToastBody";var g=n.forwardRef(function(s,a){var i=s.children,o=s.as,r=R(s,["children","as"]),c=n.useContext(L).setVisible;return o?l.createElement(o,u({onClick:function(){return c(!1)}},r,{ref:a}),i):l.createElement(D,u({onClick:function(){return c(!1)}},r,{ref:a}))});g.propTypes=u(u({},D.propTypes),{as:t.elementType});g.displayName="CToastClose";var f=n.forwardRef(function(s,a){var i=s.children,o=s.className,r=s.closeButton,c=R(s,["children","className","closeButton"]);return l.createElement("div",u({className:A("toast-header",o)},c,{ref:a}),i,r&&l.createElement(g,null))});f.propTypes={children:t.node,className:t.string,closeButton:t.bool};f.displayName="CToastHeader";var I=n.forwardRef(function(s,a){var i=s.children,o=s.className,r=s.placement,c=s.push,U=R(s,["children","className","placement","push"]),E=n.useState([]),p=E[0],b=E[1],v=n.useRef(0);n.useEffect(function(){v.current++,c&&m(c)},[c]);var m=function(w){b(function(k){return q(q([],k,!0),[l.cloneElement(w,{index:v.current,innerKey:v.current,onClose:function(B){return b(function(H){return H.filter(function(M){return M.props.index!==B})})}})],!1)})};return l.createElement(ee,{portal:typeof r=="string"},p.length>0||i?l.createElement("div",u({className:A("toaster toast-container",{"position-fixed":r,"top-0":r&&r.includes("top"),"top-50 translate-middle-y":r&&r.includes("middle"),"bottom-0":r&&r.includes("bottom"),"start-0":r&&r.includes("start"),"start-50 translate-middle-x":r&&r.includes("center"),"end-0":r&&r.includes("end")},o)},U,{ref:a}),i,p.map(function(w){return l.cloneElement(w,{visible:!0})})):null)});I.propTypes={children:t.node,className:t.string,placement:t.oneOfType([t.string,t.oneOf(["top-start","top-center","top-end","middle-start","middle-center","middle-end","bottom-start","bottom-center","bottom-end"])]),push:t.any};I.displayName="CToaster";const se=()=>{const[s,a]=n.useState(0),i=n.useRef(),o=e.jsxs(d,{children:[e.jsxs(f,{closeButton:!0,children:[e.jsx("svg",{className:"rounded me-2",width:"20",height:"20",xmlns:"http://www.w3.org/2000/svg",preserveAspectRatio:"xMidYMid slice",focusable:"false",role:"img",children:e.jsx("rect",{width:"100%",height:"100%",fill:"#007aff"})}),e.jsx("strong",{className:"me-auto",children:"CoreUI for React.js"}),e.jsx("small",{children:"7 min ago"})]}),e.jsx(h,{children:"Hello, world! This is a toast message."})]});return e.jsxs(e.Fragment,{children:[e.jsx(V,{color:"primary",onClick:()=>a(o),children:"Send a toast"}),e.jsx(I,{ref:i,push:s,placement:"top-end"})]})},de=()=>e.jsxs(_,{children:[e.jsxs(y,{xs:12,children:[e.jsx($,{href:"components/toast/"}),e.jsxs(C,{className:"mb-4",children:[e.jsxs(T,{children:[e.jsx("strong",{children:"React Toast"})," ",e.jsx("small",{children:"Basic"})]}),e.jsxs(N,{children:[e.jsx("p",{className:"text-body-secondary small",children:"Toasts are as flexible as you need and have very little required markup. At a minimum, we require a single element to contain your “toasted” content and strongly encourage a dismiss button."}),e.jsx(x,{href:"components/toast",children:e.jsxs(d,{autohide:!1,visible:!0,children:[e.jsxs(f,{closeButton:!0,children:[e.jsx("svg",{className:"rounded me-2",width:"20",height:"20",xmlns:"http://www.w3.org/2000/svg",preserveAspectRatio:"xMidYMid slice",focusable:"false",role:"img",children:e.jsx("rect",{width:"100%",height:"100%",fill:"#007aff"})}),e.jsx("strong",{className:"me-auto",children:"CoreUI for React.js"}),e.jsx("small",{children:"7 min ago"})]}),e.jsx(h,{children:"Hello, world! This is a toast message."})]})}),e.jsx(x,{href:"components/toast",children:se()})]})]})]}),e.jsx(y,{xs:12,children:e.jsxs(C,{className:"mb-4",children:[e.jsxs(T,{children:[e.jsx("strong",{children:"React Toast"})," ",e.jsx("small",{children:"Translucent"})]}),e.jsxs(N,{children:[e.jsx("p",{className:"text-body-secondary small",children:"Toasts are slightly translucent to blend in with what's below them."}),e.jsx(x,{href:"components/toast#translucent",tabContentClassName:"bg-dark",children:e.jsxs(d,{autohide:!1,visible:!0,children:[e.jsxs(f,{closeButton:!0,children:[e.jsx("svg",{className:"rounded me-2",width:"20",height:"20",xmlns:"http://www.w3.org/2000/svg",preserveAspectRatio:"xMidYMid slice",focusable:"false",role:"img",children:e.jsx("rect",{width:"100%",height:"100%",fill:"#007aff"})}),e.jsx("strong",{className:"me-auto",children:"CoreUI for React.js"}),e.jsx("small",{children:"7 min ago"})]}),e.jsx(h,{children:"Hello, world! This is a toast message."})]})})]})]})}),e.jsx(y,{xs:12,children:e.jsxs(C,{className:"mb-4",children:[e.jsxs(T,{children:[e.jsx("strong",{children:"React Toast"})," ",e.jsx("small",{children:"Stacking"})]}),e.jsxs(N,{children:[e.jsx("p",{className:"text-body-secondary small",children:"You can stack toasts by wrapping them in a toast container, which will vertically add some spacing."}),e.jsx(x,{href:"components/toast#stacking",children:e.jsxs(I,{className:"position-static",children:[e.jsxs(d,{autohide:!1,visible:!0,children:[e.jsxs(f,{closeButton:!0,children:[e.jsx("svg",{className:"rounded me-2",width:"20",height:"20",xmlns:"http://www.w3.org/2000/svg",preserveAspectRatio:"xMidYMid slice",focusable:"false",role:"img",children:e.jsx("rect",{width:"100%",height:"100%",fill:"#007aff"})}),e.jsx("strong",{className:"me-auto",children:"CoreUI for React.js"}),e.jsx("small",{children:"7 min ago"})]}),e.jsx(h,{children:"Hello, world! This is a toast message."})]}),e.jsxs(d,{autohide:!1,visible:!0,children:[e.jsxs(f,{closeButton:!0,children:[e.jsx("svg",{className:"rounded me-2",width:"20",height:"20",xmlns:"http://www.w3.org/2000/svg",preserveAspectRatio:"xMidYMid slice",focusable:"false",role:"img",children:e.jsx("rect",{width:"100%",height:"100%",fill:"#007aff"})}),e.jsx("strong",{className:"me-auto",children:"CoreUI for React.js"}),e.jsx("small",{children:"7 min ago"})]}),e.jsx(h,{children:"Hello, world! This is a toast message."})]})]})})]})]})}),e.jsx(y,{xs:12,children:e.jsxs(C,{className:"mb-4",children:[e.jsxs(T,{children:[e.jsx("strong",{children:"React Toast"})," ",e.jsx("small",{children:"Custom content"})]}),e.jsxs(N,{children:[e.jsxs("p",{className:"text-body-secondary small",children:["Customize your toasts by removing sub-components, tweaking them with"," ",e.jsx("a",{href:"https://coreui.io/docs/utilities/api",children:"utilities"}),", or by adding your own markup. Here we've created a simpler toast by removing the default"," ",e.jsx("code",{children:"<CToastHeader>"}),", adding a custom hide icon from"," ",e.jsx("a",{href:"https://coreui.io/icons/",children:"CoreUI Icons"}),", and using some"," ",e.jsx("a",{href:"https://coreui.io/docs/utilities/flex",children:"flexbox utilities"})," to adjust the layout."]}),e.jsx(x,{href:"components/toast#custom-content",children:e.jsx(d,{autohide:!1,className:"align-items-center",visible:!0,children:e.jsxs("div",{className:"d-flex",children:[e.jsx(h,{children:"Hello, world! This is a toast message."}),e.jsx(g,{className:"me-2 m-auto"})]})})}),e.jsx("p",{className:"text-body-secondary small",children:"Alternatively, you can also add additional controls and components to toasts."}),e.jsx(x,{href:"components/toast#custom-content",children:e.jsx(d,{autohide:!1,visible:!0,children:e.jsxs(h,{children:["Hello, world! This is a toast message.",e.jsxs("div",{className:"mt-2 pt-2 border-top",children:[e.jsx(V,{type:"button",color:"primary",size:"sm",children:"Take action"}),e.jsx(g,{as:V,color:"secondary",size:"sm",className:"ms-1",children:"Close"})]})]})})})]})]})}),e.jsx(y,{xs:12,children:e.jsxs(C,{className:"mb-4",children:[e.jsxs(T,{children:[e.jsx("strong",{children:"React Toast"})," ",e.jsx("small",{children:"Custom content"})]}),e.jsxs(N,{children:[e.jsxs("p",{className:"text-body-secondary small",children:["Building on the above example, you can create different toast color schemes with our"," ",e.jsx("a",{href:"https://coreui.io/docs/utilities/colors",children:"color"})," and"," ",e.jsx("a",{href:"https://coreui.io/docs/utilities/background",children:"background"})," utilities. Here we've set ",e.jsx("code",{children:'color="primary"'})," and added ",e.jsx("code",{children:".text-white"})," ","class to the ",e.jsx("code",{children:"<Ctoast>"}),", and then set ",e.jsx("code",{children:"white"})," property to our close button. For a crisp edge, we remove the default border with"," ",e.jsx("code",{children:".border-0"}),"."]}),e.jsx(x,{href:"components/toast#color-schemes",children:e.jsx(d,{autohide:!1,color:"primary",className:"text-white align-items-center",visible:!0,children:e.jsxs("div",{className:"d-flex",children:[e.jsx(h,{children:"Hello, world! This is a toast message."}),e.jsx(g,{className:"me-2 m-auto",white:!0})]})})})]})]})})]});export{de as default};
