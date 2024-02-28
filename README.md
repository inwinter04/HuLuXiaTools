## 前言
出于本人Java学习的需要，本项目使用纯Java实现，其中包含了`http通讯`、`IO流`等基础知识。

本项目目前已完成包括`发帖`，`回复`，`签到`，`图片上传`的**Sign值**生成方法，逆向不易，还请珍惜。

## 功能
程序已实现以下功能，并且集合到静态方法中
|  功能   | 类  |
|  ----  | ----  |
| 一键签到  | SignInUtility.java |
| 发帖功能  | CreatPostUtility.java |
| 评论功能  | CreateCommentUtility.java |
| 图片上传  | ImageUploader.java |
| Sign生成  | MD5Utils.java |

## 结构
以下为程序目录结构
```
├─HuLuXiaTools
│  │  README.md                                         项目自述文件
│  │                              
│  └─src
│      └─cn
│          └─iamdt
│              └─HuluxiaTools
│                  │  config.properties                 配置文件 config
│                  │  CreateCommentUtility.java         评论[模块]
│                  │  CreatPostUtility.java             发帖[模块]
│                  │  Main.java                         程序[入口]
│                  │  SignInUtility.java                签到[模块]
│                  │  
│                  └─utils
│                          AtUserData.java              艾特[组件]
│                          ConfigLoader.java            配置[组件]
│                          HttpConnection.java          网络[组件]
│                          ImageUploader.java           图片上传[组件]
│                          MD5Utils.java                加密[组件]
│
```
## 特别鸣谢
特别鸣谢吾爱破解大佬**佚名RJ**提供签到Sign生成的思路！ [文章链接](https://www.52pojie.cn/thread-1784167-1-1.html)

特别鸣谢**林魂**大佬提供图片Sign生成的思路！ [林魂云API](https://api.linhun.vip/doc/hlximg.html#)


***
**本仓库内代码仅供个人研究和学习使用，请勿用于商业用途，下载后请于24小时内删除，严禁用于非法用途！**
