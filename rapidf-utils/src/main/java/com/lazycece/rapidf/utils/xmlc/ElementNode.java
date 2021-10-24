/*
 *    Copyright 2021 lazycece<lazycece@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lazycece.rapidf.utils.xmlc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lazycece
 * @date 2018/4/6
 */
public class ElementNode {

    private String name;
    private List<NameSpace> nameSpaces;
    private List<Attribute> attributes;
    private String value;
    private String cData;
    private List<ElementNode> children;

    public ElementNode() {
        this.nameSpaces = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public ElementNode(String name, List<NameSpace> nameSpaces, List<Attribute> attributes,
                       String value, String cData, List<ElementNode> children) {
        this.name = name;
        this.nameSpaces = nameSpaces;
        this.attributes = attributes;
        this.value = value;
        this.cData = cData;
        this.children = children;
    }

    public String getcData() {
        return cData;
    }

    public void setcData(String cData) {
        this.cData = cData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    public void setNameSpaces(List<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ElementNode> getChildren() {
        return children;
    }

    public void setChildren(List<ElementNode> children) {
        this.children = children;
    }

    public void addNameSpace(NameSpace nameSpace) {
        this.nameSpaces.add(nameSpace);
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void addChild(ElementNode child) {
        this.children.add(child);
    }

}
