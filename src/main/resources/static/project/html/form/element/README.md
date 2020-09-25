现有控件类型
``` javascript
F2CForm.constant("ELEMENT_TYPE", {
        INPUT: "input",
        SELECT: "select",
        TEXTAREA: "textarea",
        CHECKBOX: "checkbox",
        RADIO: "radio",
        DATE: "date",
        LINK: "link",
        SWITCH: "switch",
    });
```

所有控件的html文件都放到element目录下，每个控件3个页面，分别为:
- <type>-design.html 设计时显示页面
- <type>-config.html 设计时配置页面
- <type>-runtime.html 运行时显示页面
- 例如: input-design.html, input-config.html, input-runtime.html