import{l as f,a as h,j as e,C as S}from"./index-mjNz_a55.js";import{b as w}from"./cil-monitor-CJVwihF0.js";import{C as g,a as m}from"./CRow-BEUl781V.js";import{C as y,a as k}from"./CCardBody-yIJap6bL.js";import{C as B}from"./CCardHeader-B53J8VH_.js";import{C as T}from"./CForm-BsaidCcj.js";import{C as E}from"./CFormCheck-D7T3AKfk.js";import{C as P}from"./CButton-BgS9oSAt.js";import{C as R,a as v,b as C,c as j,d as O,e as o}from"./CTable-CKG8Lttz.js";import{C as F}from"./CAlert-mx2euOG5.js";import{c as H}from"./cil-burn-DKRRBsQY.js";import"./DefaultLayout-DJn0iQsI.js";const L=(a,l)=>{var r,d;const t=(d=(r=a==null?void 0:a.data)==null?void 0:r.lowestPrice)==null?void 0:d.products,i=Object.keys(l);if(t)return t.sort((c,n)=>i.indexOf(c.category)-i.indexOf(n.category)),{products:t,brandName:a.data.lowestPrice.brandName,totalPrice:a.data.lowestPrice.totalPrice}},Q=()=>{const a=f(s=>s.backendUrl),l=f(s=>s.categories),[t,i]=h.useState(!1),[r,d]=h.useState(null),[c,n]=h.useState(!1),[u,p]=h.useState(null),b=()=>{n(!0),p(null),fetch(a+"/api/v1/products/set?price=LOWEST&singleBrand="+t,{method:"GET",headers:{"Cache-Control":"no-cache",Pragma:"no-cache",Expires:"0"}}).then(s=>s.ok?s.json():s.json().then(x=>{const N=`Network error: ${s.status} - ${x.message}`;throw new Error(N)})).then(s=>{d(L(s,l)),n(!1)}).catch(s=>{p(s),n(!1)})};return e.jsx(g,{children:e.jsx(m,{xs:12,children:e.jsxs(y,{className:"mb-4",children:[e.jsx(B,{children:e.jsx("strong",{children:"최저가 세트"})}),e.jsxs(k,{children:[e.jsxs(T,{className:"row g-3 justify-content-end",onSubmit:b,children:[e.jsx(m,{xs:"auto",className:"text-end",children:e.jsx(E,{id:"singleBrand",label:"단일 브랜드",checked:t,onChange:s=>i(s.target.checked)})}),e.jsx(m,{xs:"auto",className:"text-end",children:e.jsxs(P,{type:"submit",color:"primary",variant:"outline",size:"sm",disabled:c,children:[c&&e.jsx(S,{as:"span",size:"sm","aria-hidden":"true"}),!c&&e.jsx(e.Fragment,{children:"검색"})]})})]}),(r==null?void 0:r.brandName)&&e.jsx(g,{children:e.jsx(m,{children:e.jsx("h5",{children:e.jsxs("b",{children:["브랜드: ",r.brandName]})})})}),e.jsxs(R,{hover:!0,children:[e.jsx(v,{children:e.jsxs(C,{children:[e.jsx(j,{scope:"col",children:"카테고리"}),e.jsx(j,{scope:"col",children:"브랜드"}),e.jsx(j,{scope:"col",children:"가격"})]})}),e.jsxs(O,{children:[r&&r.products.map((s,x)=>e.jsxs(C,{children:[e.jsx(o,{children:l[s.category]}),e.jsx(o,{children:s.brandName}),e.jsx(o,{children:s.price})]},x)),r&&e.jsxs(C,{children:[e.jsx(o,{colSpan:2,children:e.jsx("b",{children:"총액"})}),e.jsx(o,{children:r.totalPrice})]})]})]}),u&&e.jsxs(F,{color:"danger",className:"d-flex align-items-center",children:[e.jsx(w,{icon:H,className:"flex-shrink-0 me-2",width:24,height:24}),e.jsx("div",{children:u.message})]})]})]})})})};export{Q as default};
