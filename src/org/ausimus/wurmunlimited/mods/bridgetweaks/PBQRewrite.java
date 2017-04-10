package org.ausimus.wurmunlimited.mods.bridgetweaks;

import javassist.*;
import javassist.bytecode.Descriptor;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import static org.ausimus.wurmunlimited.mods.bridgetweaks.Init.*;

class PBQRewrite {
    PBQRewrite() {
        try {
            CtClass ctClass00 = HookManager.getInstance().getClassPool().get("com.wurmonline.server.questions.PlanBridgeQuestion");
            CtMethod method00 = ctClass00.getMethod("sendQuestion", Descriptor.ofMethod(CtPrimitiveType.voidType, null));
            //Would have done a length modifier, but weird shit happens when changing maxpoletiles and maxdioptiles :(.
            method00.setBody("{\n" +
                    "        this.grabFloorLevels();\n" +
                    "        this.bmlLines = 13;\n" +
                    "        this.iconLines = 0;\n" +
                    "        java.lang.StringBuilder buf = new java.lang.StringBuilder();\n" +
                    "        java.lang.StringBuilder bridges = new java.lang.StringBuilder();\n" +
                    "        boolean doneWoodCheck = false;\n" +
                    "        boolean doneStoneCheck = false;\n" +
                    "        String[] woodFail = new String[]{\"\", \"\"};\n" +
                    "        String[] stoneFail = new String[]{\"\", \"\"};\n" +
                    "        this.bridgeCount = 0;\n" +
                    "        String bridge = \"\";\n" +
                    "        String bridgeArea = \"Planned bridge area is \" + this.length + \" tile\" + (this.length == 1?\"\":\"s\") + \" long and \" + this.width + \" tile\" + (this.width == 1?\"\":\"s\") + \" wide.\";\n" +
                    "        this.getResponder().getCommunicator().sendNormalServerMessage(bridgeArea);\n" +
                    "        buf.append(this.getBmlHeaderWithScrollAndQuestion());\n" +
                    "        buf.append(\"label{text=\\\"\" + bridgeArea + \"\\\"};\");\n" +
                    "        buf.append(\"label{type=\\\"bold\\\";text=\\\"Bridge types available...\\\"};\");\n" +
                    "        this.fail = \"\";\n" +
                    "        this.reason = \"\";\n" +
                    "        bridge = \"\";\n" +
                    "        int maxLength = 38;\n" +
                    "        com.wurmonline.server.skills.Skill requiredSkill = this.getResponder().getSkills().getSkillOrLearn(1014);\n" +
                    "        String[] reply;\n" +
                    "        if(this.width > " + RopeMaxWidth + ") {\n" +
                    "            this.fail = \"Too Wide\";\n" +
                    "            this.reason = \"Rope bridges can only be " + RopeMaxWidth + " tile wide\";\n" +
                    "        } else if(this.length > maxLength) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Rope bridges are restricted to \" + maxLength + \" tiles long\";\n" +
                    "        } else if(this.hasLowSkill(requiredSkill, 10, maxLength, true)) {\n" +
                    "            this.fail = \"Low Skill\";\n" +
                    "            if(requiredSkill.getKnowledge(0.0D) < 10.0D) {\n" +
                    "                this.reason = \"You need at least 10 ropemaking skill to plan any rope bridge.\";\n" +
                    "            } else {\n" +
                    "                this.reason = \"You dont have enough ropemaking skill for this length rope bridge.\";\n" +
                    "            }\n" +
                    "        } else {\n" +
                    "            reply = com.wurmonline.server.structures.PlanBridgeMethods.isBuildingOk(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.ROPE.getCode(), this.dir, this.start, this.startFloorlevel, this.end, this.endFloorlevel);\n" +
                    "            this.fail = reply[0];\n" +
                    "            this.reason = reply[1];\n" +
                    "        }\n" +
                    "\n" +
                    "        if(this.fail.length() == 0) {\n" +
                    "            if(this.length == 1) {\n" +
                    "                bridge = \"E\";\n" +
                    "            } else {\n" +
                    "                bridge = this.makeArch(\"a\", this.length, 'C', \"A\");\n" +
                    "                this.ropeHeightsOk(bridge, this.calcMinSag());\n" +
                    "            }\n" +
                    "        }\n" +
                    "\n" +
                    "        bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.ROPE, true, \"Rope\", bridge));\n" +
                    "        this.fail = \"\";\n" +
                    "        this.reason = \"\";\n" +
                    "        bridge = \"\";\n" +
                    "        maxLength = 38;\n" +
                    "        requiredSkill = this.getResponder().getSkills().getSkillOrLearn(1005);\n" +
                    "        if(this.width > " + WoodMaxWidth + ") {\n" +
                    "            this.fail = \"Too Wide\";\n" +
                    "            this.reason = \"Wood bridges can only be a maximum of " + WoodMaxWidth + " tiles wide.\";\n" +
                    "        } else if(this.heightDiff > 20 * this.length) {\n" +
                    "            this.fail = \"Too Steep\";\n" +
                    "            this.reason = \"The slope of part of the bridge would exceed 20 dirt.\";\n" +
                    "        } else if(this.length > maxLength) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Wood bridges are restricted to \" + maxLength + \" tiles long\";\n" +
                    "        } else if(this.hasLowSkill(requiredSkill, 10, maxLength, false)) {\n" +
                    "            this.fail = \"Low Skill\";\n" +
                    "            if(requiredSkill.getKnowledge(0.0D) < 10.0D) {\n" +
                    "                this.reason = \"You need at least 10 carpentry skill to plan any wood bridge.\";\n" +
                    "            } else {\n" +
                    "                this.reason = \"You dont have enough carpentry skill for this length wood bridge.\";\n" +
                    "            }\n" +
                    "        } else if(this.length <= 5 || this.start.getH() >= 0 && this.end.getH() >= 0) {\n" +
                    "            reply = com.wurmonline.server.structures.PlanBridgeMethods.isBuildingOk(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.WOOD.getCode(), this.dir, this.start, this.startFloorlevel, this.end, this.endFloorlevel);\n" +
                    "            this.fail = reply[0];\n" +
                    "            this.reason = reply[1];\n" +
                    "            doneWoodCheck = true;\n" +
                    "            woodFail = reply;\n" +
                    "        } else {\n" +
                    "            this.fail = \"Too Low\";\n" +
                    "            this.reason = \"Both ends of a wood bridge (if it has supports) need to be 0 above water.\";\n" +
                    "        }\n" +
                    "\n" +
                    "        if(this.fail.length() > 0) {\n" +
                    "            bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.WOOD, false, \"Flat Wood\", bridge));\n" +
                    "        } else {\n" +
                    "            bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.WOOD, \"Flat Wood\", this.getWoodSpan(this.length)));\n" +
                    "        }\n" +
                    "\n" +
                    "        this.fail = \"\";\n" +
                    "        this.reason = \"\";\n" +
                    "        bridge = \"\";\n" +
                    "        maxLength = 38;\n" +
                    "        requiredSkill = this.getResponder().getSkills().getSkillOrLearn(1013);\n" +
                    "        if(this.width > " + StoneMaxWidth + ") {\n" +
                    "            this.fail = \"Too Wide\";\n" +
                    "            this.reason = \"Brick bridges are limited to " + StoneMaxWidth + " tiles wide.\";\n" +
                    "        } else if(this.heightDiff > 20 * this.length) {\n" +
                    "            this.fail = \"Too Steep\";\n" +
                    "            this.reason = \"The slope of part of the bridge would exceed 20 dirt.\";\n" +
                    "        } else if(this.length > maxLength) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Brick bridges are restricted to \" + maxLength + \" tiles long\";\n" +
                    "        } else if(this.hasLowSkill(requiredSkill, 30, maxLength, false)) {\n" +
                    "            this.fail = \"Low Skill\";\n" +
                    "            if(requiredSkill.getKnowledge(0.0D) < 30.0D) {\n" +
                    "                this.reason = \"You need at least 30 masonry skill to make any brick bridge.\";\n" +
                    "            } else {\n" +
                    "                this.reason = \"You dont have enough masonry skill for this length brick bridge.\";\n" +
                    "            }\n" +
                    "        } else if(this.length <= 5 || this.start.getH() >= 0 && this.end.getH() >= 0) {\n" +
                    "            reply = com.wurmonline.server.structures.PlanBridgeMethods.isBuildingOk(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.BRICK.getCode(), this.dir, this.start, this.startFloorlevel, this.end, this.endFloorlevel);\n" +
                    "            this.fail = reply[0];\n" +
                    "            this.reason = reply[1];\n" +
                    "            doneStoneCheck = true;\n" +
                    "            stoneFail = reply;\n" +
                    "        } else {\n" +
                    "            this.fail = \"Too Low\";\n" +
                    "            this.reason = \"Both ends of a Brick bridge (if it has supports) need to be 0 above water.\";\n" +
                    "        }\n" +
                    "\n" +
                    "        if(this.fail.length() > 0) {\n" +
                    "            bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.BRICK, false, \"Flat Brick\", bridge));\n" +
                    "        } else {\n" +
                    "            bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.BRICK, \"Flat Brick\", this.getBrickSpan(this.length)));\n" +
                    "        }\n" +
                    "\n" +
                    "        this.fail = \"\";\n" +
                    "        this.reason = \"\";\n" +
                    "        bridge = \"\";\n" +
                    "        maxLength = 38;\n" +
                    "        requiredSkill = this.getResponder().getSkills().getSkillOrLearn(1013);\n" +
                    "        if(this.width > " + MarbleMaxWidth + ") {\n" +
                    "            this.fail = \"Too Wide\";\n" +
                    "            this.reason = \"Marble bridges are limited to " + MarbleMaxWidth + " tiles wide.\";\n" +
                    "        } else if(this.heightDiff > 20 * this.length) {\n" +
                    "            this.fail = \"Too Steep\";\n" +
                    "            this.reason = \"The slope of part of the bridge would exceed 20 dirt.\";\n" +
                    "        } else if(this.length > maxLength) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Marble bridges are restricted to \" + maxLength + \" tiles long\";\n" +
                    "        } else if(this.hasLowSkill(requiredSkill, 40, maxLength, false)) {\n" +
                    "            this.fail = \"Low Skill\";\n" +
                    "            if(requiredSkill.getKnowledge(0.0D) < 40.0D) {\n" +
                    "                this.reason = \"You need at least 40 masonry skill to make any marble bridge.\";\n" +
                    "            } else {\n" +
                    "                this.reason = \"You dont have enough masonry skill for this length marble bridge.\";\n" +
                    "            }\n" +
                    "        } else if(this.length > 5 && (this.start.getH() < 0 || this.end.getH() < 0)) {\n" +
                    "            this.fail = \"Too Low\";\n" +
                    "            this.reason = \"Both ends of a Marble bridge (if it has supports) need to be 0 above water.\";\n" +
                    "        } else if(doneStoneCheck) {\n" +
                    "            this.fail = stoneFail[0];\n" +
                    "            this.reason = stoneFail[1];\n" +
                    "        } else {\n" +
                    "            reply = com.wurmonline.server.structures.PlanBridgeMethods.isBuildingOk(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.MARBLE.getCode(), this.dir, this.start, this.startFloorlevel, this.end, this.endFloorlevel);\n" +
                    "            this.fail = reply[0];\n" +
                    "            this.reason = reply[1];\n" +
                    "            stoneFail = reply;\n" +
                    "            doneStoneCheck = true;\n" +
                    "        }\n" +
                    "\n" +
                    "        if(this.fail.length() > 0) {\n" +
                    "            bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.MARBLE, false, \"Flat Marble\", bridge));\n" +
                    "        } else {\n" +
                    "            bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.MARBLE, \"Flat Marble\", this.getBrickSpan(this.length)));\n" +
                    "        }\n" +
                    "\n" +
                    "        this.fail = \"\";\n" +
                    "        this.reason = \"\";\n" +
                    "        bridge = \"\";\n" +
                    "        maxLength = 38;\n" +
                    "        requiredSkill = this.getResponder().getSkills().getSkillOrLearn(1005);\n" +
                    "        if(this.width > " + WoodMaxWidth + ") {\n" +
                    "            this.fail = \"Too Wide\";\n" +
                    "            this.reason = \"Wood bridges are limited to " + WoodMaxWidth + " tiles wide.\";\n" +
                    "        } else if(this.length < 2) {\n" +
                    "            this.fail = \"Too Short\";\n" +
                    "            this.reason = \"Need to have a minium of 2 tiles to form an arch.\";\n" +
                    "        } else if(this.length * 2 > com.wurmonline.server.structures.PlanBridgeMethods.getHighest().length) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Arched wood bridges are restricted to \" + (com.wurmonline.server.structures.PlanBridgeMethods.getHighest().length >>> 1) + \" tiles long\";\n" +
                    "        } else if(this.heightDiff > com.wurmonline.server.structures.PlanBridgeMethods.getHighest()[this.length * 2]) {\n" +
                    "            this.fail = \"Too Steep\";\n" +
                    "            this.reason = \"The slope of part of the bridge would exceed 20 dirt.\";\n" +
                    "        } else if(this.length > maxLength) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Arched wood bridges are restricted to \" + maxLength + \" tiles long\";\n" +
                    "        } else if(this.hasLowSkill(requiredSkill, 20, maxLength, true)) {\n" +
                    "            this.fail = \"Low Skill\";\n" +
                    "            if(requiredSkill.getKnowledge(0.0D) < 10.0D) {\n" +
                    "                this.reason = \"You need at least 10 carpentry skill to make any arched wood bridge.\";\n" +
                    "            } else {\n" +
                    "                this.reason = \"You dont have enough carpentry skill for this length arched wood bridge.\";\n" +
                    "            }\n" +
                    "        } else if(this.length > 11 && (this.start.getH() < 0 || this.end.getH() < 0)) {\n" +
                    "            this.fail = \"Too Low\";\n" +
                    "            this.reason = \"Both ends of a wood arched bridge need to be 0 above water.\";\n" +
                    "        } else if(doneWoodCheck) {\n" +
                    "            this.fail = woodFail[0];\n" +
                    "            this.reason = woodFail[1];\n" +
                    "        } else {\n" +
                    "            reply = com.wurmonline.server.structures.PlanBridgeMethods.isBuildingOk(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.WOOD.getCode(), this.dir, this.start, this.startFloorlevel, this.end, this.endFloorlevel);\n" +
                    "            this.fail = reply[0];\n" +
                    "            this.reason = reply[1];\n" +
                    "        }\n" +
                    "\n" +
                    "        if(this.fail.length() == 0) {\n" +
                    "            bridge = this.addArchs(this.length, false);\n" +
                    "        }\n" +
                    "\n" +
                    "        bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.WOOD, true, \"Arched Wood\", bridge));\n" +
                    "        this.fail = \"\";\n" +
                    "        this.reason = \"\";\n" +
                    "        bridge = \"\";\n" +
                    "        maxLength = 38;\n" +
                    "        requiredSkill = this.getResponder().getSkills().getSkillOrLearn(1013);\n" +
                    "        if(this.width > " + StoneMaxWidth + ") {\n" +
                    "            this.fail = \"Too Wide\";\n" +
                    "            this.reason = \"Brick bridges are limited to " + StoneMaxWidth + " tiles wide.\";\n" +
                    "        } else if(this.length < 2) {\n" +
                    "            this.fail = \"Too Short\";\n" +
                    "            this.reason = \"Need to have a minium of 2 tiles to form an arch.\";\n" +
                    "        } else if(this.length * 2 > com.wurmonline.server.structures.PlanBridgeMethods.getHighest().length) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Arched brick bridges are restricted to \" + (com.wurmonline.server.structures.PlanBridgeMethods.getHighest().length >>> 1) + \" tiles long\";\n" +
                    "        } else if(this.heightDiff > com.wurmonline.server.structures.PlanBridgeMethods.getHighest()[this.length * 2]) {\n" +
                    "            this.fail = \"Too Steep\";\n" +
                    "            this.reason = \"The slope of part of the bridge would exceed 20 dirt.\";\n" +
                    "        } else if(this.length > maxLength) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Arched brick bridges are restricted to \" + maxLength + \" tiles long\";\n" +
                    "        } else if(this.hasLowSkill(requiredSkill, 50, maxLength, true)) {\n" +
                    "            this.fail = \"Low Skill\";\n" +
                    "            if(requiredSkill.getKnowledge(0.0D) < 50.0D) {\n" +
                    "                this.reason = \"You need at least 50 masonry skill to make any arched brick bridge.\";\n" +
                    "            } else {\n" +
                    "                this.reason = \"You dont have enough masonry skill for this length arched brick bridge.\";\n" +
                    "            }\n" +
                    "        } else if(this.length <= 8 || this.start.getH() >= 0 && this.end.getH() >= 0) {\n" +
                    "            if(doneStoneCheck) {\n" +
                    "                this.fail = stoneFail[0];\n" +
                    "                this.reason = stoneFail[1];\n" +
                    "            } else {\n" +
                    "                reply = com.wurmonline.server.structures.PlanBridgeMethods.isBuildingOk(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.BRICK.getCode(), this.dir, this.start, this.startFloorlevel, this.end, this.endFloorlevel);\n" +
                    "                this.fail = reply[0];\n" +
                    "                this.reason = reply[1];\n" +
                    "                stoneFail = reply;\n" +
                    "                doneStoneCheck = true;\n" +
                    "            }\n" +
                    "        } else {\n" +
                    "            this.fail = \"Too Low\";\n" +
                    "            this.reason = \"Both ends of a brick arched bridge need to be 0 above water.\";\n" +
                    "        }\n" +
                    "\n" +
                    "        if(this.fail.length() == 0) {\n" +
                    "            bridge = this.addArchs(this.length, true);\n" +
                    "            if(com.wurmonline.server.Servers.isThisATestServer() && this.getResponder().getPower() >= 2) {\n" +
                    "                this.getResponder().getCommunicator().sendNormalServerMessage(\"(\" + this.length + \":\" + bridge + \")\");\n" +
                    "            }\n" +
                    "        }\n" +
                    "\n" +
                    "        bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.BRICK, true, \"Arched Brick\", bridge));\n" +
                    "        this.fail = \"\";\n" +
                    "        this.reason = \"\";\n" +
                    "        bridge = \"\";\n" +
                    "        maxLength = 38;\n" +
                    "        requiredSkill = this.getResponder().getSkills().getSkillOrLearn(1013);\n" +
                    "        if(this.width > " + MarbleMaxWidth + ") {\n" +
                    "            this.fail = \"Too Wide\";\n" +
                    "            this.reason = \"Marble bridges are limited to " + MarbleMaxWidth + " tiles wide.\";\n" +
                    "        } else if(this.length < 2) {\n" +
                    "            this.fail = \"Too Short\";\n" +
                    "            this.reason = \"Need to have a minium of 2 tiles to form an arch.\";\n" +
                    "        } else if(this.length * 2 > com.wurmonline.server.structures.PlanBridgeMethods.getHighest().length) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Arched marble bridges are restricted to \" + (com.wurmonline.server.structures.PlanBridgeMethods.getHighest().length >>> 1) + \" tiles long\";\n" +
                    "        } else if(this.heightDiff > com.wurmonline.server.structures.PlanBridgeMethods.getHighest()[this.length * 2]) {\n" +
                    "            this.fail = \"Too Steep\";\n" +
                    "            this.reason = \"The slope of part of the bridge would exceed 20 dirt.\";\n" +
                    "        } else if(this.length > maxLength) {\n" +
                    "            this.fail = \"Too Long\";\n" +
                    "            this.reason = \"Arched marble bridges are restricted to \" + maxLength + \" tiles long\";\n" +
                    "        } else if(this.hasLowSkill(requiredSkill, 60, maxLength, true)) {\n" +
                    "            this.fail = \"Low Skill\";\n" +
                    "            if(requiredSkill.getKnowledge(0.0D) < 60.0D) {\n" +
                    "                this.reason = \"You need at least 60 masonry skill to make any arched marble bridge.\";\n" +
                    "            } else {\n" +
                    "                this.reason = \"You dont have enough masonry skill for this length arched marble bridge.\";\n" +
                    "            }\n" +
                    "        } else if(this.length > 8 && (this.start.getH() < 0 || this.end.getH() < 0)) {\n" +
                    "            this.fail = \"Too Low\";\n" +
                    "            this.reason = \"Both ends of a marble arched bridge need to be 0 above water.\";\n" +
                    "        } else if(doneStoneCheck) {\n" +
                    "            this.fail = stoneFail[0];\n" +
                    "            this.reason = stoneFail[1];\n" +
                    "        } else {\n" +
                    "            reply = com.wurmonline.server.structures.PlanBridgeMethods.isBuildingOk(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.MARBLE.getCode(), this.dir, this.start, this.startFloorlevel, this.end, this.endFloorlevel);\n" +
                    "            this.fail = reply[0];\n" +
                    "            this.reason = reply[1];\n" +
                    "            doneStoneCheck = true;\n" +
                    "        }\n" +
                    "\n" +
                    "        if(this.fail.length() == 0) {\n" +
                    "            bridge = this.addArchs(this.length, true);\n" +
                    "        }\n" +
                    "\n" +
                    "        bridges.append(this.addBridgeEntry(com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial.MARBLE, true, \"Arched Marble\", bridge));\n" +
                    "        buf.append(\"table{rows=\\\"\" + (this.bridgeCount + 1) + \"\\\";cols=\\\"3\\\";\");\n" +
                    "        buf.append(bridges);\n" +
                    "        buf.append(\"radio{group=\\\"bridgereply\\\";id=\\\"0,\\\";selected=\\\"true\\\"};label{text=\\\"None\\\"};label{text=\\\"\\\"};\");\n" +
                    "        buf.append(\"}\");\n" +
                    "        buf.append(\"label{text=\\\"\\\"}\");\n" +
                    "        buf.append(\"label{type=\\\"bolditalic\\\";text=\\\"Warning: As a bridge is a structure you will not be able to terraform or plant under it.\\\"}\");\n" +
                    "        buf.append(\"label{text=\\\"\\\"}\");\n" +
                    "        buf.append(this.createAnswerButton2(\"Next\"));\n" +
                    "        int bmlHeight = this.bmlLines * 15 + this.iconLines * 34 + 35;\n" +
                    "        int bmlWidth = Math.max(470, 55 + (this.length + 2) * 34);\n" +
                    "        this.getResponder().getCommunicator().sendBml(bmlWidth, bmlHeight, true, true, buf.toString(), 200, 200, 200, this.title);\n" +
                    "    }");
        } catch (CannotCompileException | NotFoundException ex) {
            throw new HookException(ex);
        }
    }
}