/** layui-v1.0.7 LGPL License By */
;!function (e) {
    "use strict";
    var t = function () {
        this.v = "1.0.7"
    };
    t.fn = t.prototype;
    var n = document, o = t.fn.cache = {}, i = function () {
        var e = n.scripts, t = e[e.length - 1].src;
        return t.substring(0, t.lastIndexOf("/") + 1)
    }(), r = function (t) {
        e.console && console.error && console.error("Layui hint: " + t)
    }, u = "undefined" != typeof opera && "[object Opera]" === opera.toString(), l = {
        layer: "modules/layer",
        laydate: "modules/laydate",
        laypage: "modules/laypage",
        laytpl: "modules/laytpl",
        layim: "modules/layim",
        layedit: "modules/layedit",
        form: "modules/form",
        upload: "modules/upload",
        tree: "modules/tree",
        slide: "modules/slide",
        table: "modules/table",
        element: "modules/element",
        util: "modules/util",
        flow: "modules/flow",
        code: "modules/code",
        single: "modules/single",
        mobile: "modules/mobile",
        jquery: "lib/jquery",
        "layui.mod": "dest/layui.mod"
    };
    o.modules = {}, o.status = {}, o.timeout = 10, o.event = {}, t.fn.define = function (e, t) {
        var n = this, i = "function" == typeof e, r = function () {
            return "function" == typeof t && t(function (e, t) {
                layui[e] = t, o.status[e] = !0
            }), this
        };
        return i && (t = e, e = []), layui["layui.all"] ? r.call(n) : (n.use(e, r), n)
    }, t.fn.use = function (e, t, a) {
        function s(e, t) {
            var n = "PLaySTATION 3" === navigator.platform ? /^complete$/ : /^(complete|loaded)$/;
            ("load" === e.type || n.test((e.currentTarget || e.srcElement).readyState)) && (o.modules[m] = t, y.removeChild(v), function i() {
                return ++p > 1e3 * o.timeout / 4 ? r(m + " is not a valid module") : void(o.status[m] ? c() : setTimeout(i, 4))
            }())
        }

        function c() {
            a.push(layui[m]), e.length > 1 ? f.use(e.slice(1), t, a) : "function" == typeof t && t.apply(layui, a)
        }

        var f = this, d = o.dir = o.dir ? o.dir : i, y = n.getElementsByTagName("head")[0];
        e = "string" == typeof e ? [e] : e, window.jQuery && jQuery.fn.on && (f.each(e, function (t, n) {
            "jquery" === n && e.splice(t, 1)
        }), layui.jquery = jQuery);
        var m = e[0], p = 0;
        if (a = a || [], o.host = o.host || (d.match(/\/\/([\s\S]+?)\//) || ["//" + location.host + "/"])[0], 0 === e.length || layui["layui.all"] && l[m]) return "function" == typeof t && t.apply(layui, a), f;
        var v = n.createElement("script"), h = (l[m] ? d + "lay/" : o.base || "") + (f.modules[m] || m) + ".js";
        return v.async = !0, v.charset = "utf-8", v.src = h + function () {
            var e = o.version === !0 ? o.v || (new Date).getTime() : o.version || "";
            return e ? "?v=" + e : ""
        }(), o.modules[m] ? !function g() {
            return ++p > 1e3 * o.timeout / 4 ? r(m + " is not a valid module") : void("string" == typeof o.modules[m] && o.status[m] ? c() : setTimeout(g, 4))
        }() : (y.appendChild(v), !v.attachEvent || v.attachEvent.toString && v.attachEvent.toString().indexOf("[native code") < 0 || u ? v.addEventListener("load", function (e) {
            s(e, h)
        }, !1) : v.attachEvent("onreadystatechange", function (e) {
            s(e, h)
        })), o.modules[m] = h, f
    }, t.fn.getStyle = function (t, n) {
        var o = t.currentStyle ? t.currentStyle : e.getComputedStyle(t, null);
        return o[o.getPropertyValue ? "getPropertyValue" : "getAttribute"](n)
    }, t.fn.link = function (e, t, i) {
        var u = this, l = n.createElement("link"), a = n.getElementsByTagName("head")[0];
        "string" == typeof t && (i = t);
        var s = (i || e).replace(/\.|\//g, ""), c = l.id = "layuicss-" + s, f = 0;
        l.rel = "stylesheet", l.href = e + (o.debug ? "?v=" + (new Date).getTime() : ""), l.media = "all", n.getElementById(c) || a.appendChild(l), "function" == typeof t && !function d() {
            return ++f > 1e3 * o.timeout / 100 ? r(e + " timeout") : void(1989 === parseInt(u.getStyle(n.getElementById(c), "width")) ? function () {
                t()
            }() : setTimeout(d, 100))
        }()
    }, t.fn.addcss = function (e, t, n) {
        layui.link(o.dir + "css/" + e, t, n)
    }, t.fn.img = function (e, t, n) {
        var o = new Image;
        return o.src = e, o.complete ? t(o) : (o.onload = function () {
            o.onload = null, t(o)
        }, void(o.onerror = function (e) {
            o.onerror = null, n(e)
        }))
    }, t.fn.config = function (e) {
        e = e || {};
        for (var t in e) o[t] = e[t];
        return this
    }, t.fn.modules = function () {
        var e = {};
        for (var t in l) e[t] = l[t];
        return e
    }(), t.fn.extend = function (e) {
        var t = this;
        e = e || {};
        for (var n in e) t[n] || t.modules[n] ? r("模块名 " + n + " 已被占用") : t.modules[n] = e[n];
        return t
    }, t.fn.router = function (e) {
        for (var t, n = (e || location.hash).replace(/^#/, "").split("/") || [], o = {dir: []}, i = 0; i < n.length; i++) t = n[i].split("="), /^\w+=/.test(n[i]) ? function () {
            "dir" !== t[0] && (o[t[0]] = t[1])
        }() : o.dir.push(n[i]), t = null;
        return o
    }, t.fn.data = function (t, n) {
        if (t = t || "layui", e.JSON && e.JSON.parse) {
            if (null === n) return delete localStorage[t];
            n = "object" == typeof n ? n : {key: n};
            try {
                var o = JSON.parse(localStorage[t])
            } catch (i) {
                var o = {}
            }
            return n.value && (o[n.key] = n.value), n.remove && delete o[n.key], localStorage[t] = JSON.stringify(o), n.key ? o[n.key] : o
        }
    }, t.fn.device = function (t) {
        var n = navigator.userAgent.toLowerCase(), o = function (e) {
            var t = new RegExp(e + "/([^\\s\\_\\-]+)");
            return e = (n.match(t) || [])[1], e || !1
        }, i = {
            os: function () {
                return /windows/.test(n) ? "windows" : /linux/.test(n) ? "linux" : /iphone|ipod|ipad|ios/.test(n) ? "ios" : void 0
            }(), ie: function () {
                return !!(e.ActiveXObject || "ActiveXObject" in e) && ((n.match(/msie\s(\d+)/) || [])[1] || "11")
            }(), weixin: o("micromessenger")
        };
        return t && !i[t] && (i[t] = o(t)), i.android = /android/.test(n), i.ios = "ios" === i.os, i
    }, t.fn.hint = function () {
        return {error: r}
    }, t.fn.each = function (e, t) {
        var n, o = this;
        if ("function" != typeof t) return o;
        if (e = e || [], e.constructor === Object) {
            for (n in e) if (t.call(e[n], n, e[n])) break
        } else for (n = 0; n < e.length && !t.call(e[n], n, e[n]); n++) ;
        return o
    }, t.fn.stope = function (t) {
        t = t || e.event, t.stopPropagation ? t.stopPropagation() : t.cancelBubble = !0
    }, t.fn.onevent = function (e, t, n) {
        return "string" != typeof e || "function" != typeof n ? this : (o.event[e + "." + t] = [n], this)
    }, t.fn.event = function (e, t, n) {
        var i = this, r = null, u = t.match(/\(.*\)$/) || [], l = (t = e + "." + t).replace(u, ""),
            a = function (e, t) {
                var o = t && t.call(i, n);
                o === !1 && null === r && (r = !1)
            };
        return layui.each(o.event[l], a), u[0] && layui.each(o.event[t], a), r
    }, e.layui = new t
}(window);