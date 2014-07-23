package com.sk.cache.wrappers;

import com.sk.cache.wrappers.loaders.NpcDefinitionLoader;
import com.sk.cache.wrappers.protocol.BasicProtocol;
import com.sk.cache.wrappers.protocol.ExtraAttributeReader;
import com.sk.cache.wrappers.protocol.ProtocolGroup;
import com.sk.cache.wrappers.protocol.StaticLocReader;
import com.sk.cache.wrappers.protocol.extractor.ArrayExtractor;
import com.sk.cache.wrappers.protocol.extractor.FieldExtractor;
import com.sk.cache.wrappers.protocol.extractor.ParseType;
import com.sk.cache.wrappers.protocol.extractor.StaticExtractor;
import com.sk.cache.wrappers.protocol.extractor.StreamExtractor;
import com.sk.datastream.Stream;

public class NpcDefinition extends ProtocolWrapper {

	public int combatLevel = -1;
	public int headIcon = -1;
	public boolean clickable = true;
	public boolean visible = true;
	public String[] actions = new String[5];
	public String name;

	public int scriptId, configId;
	public int[] childrenIds;

	public NpcDefinition(NpcDefinitionLoader loader, int id) {
		super(loader, id, protocol);
	}

	private static final ProtocolGroup protocol = new ProtocolGroup();
	static {
		new StaticLocReader(106, 118) {
			@Override
			public void read(Object obj, int type, Stream s) {
				int script = s.getUShort();
				int config = s.getUShort();
				int ending = type == 118 ? s.getUShort() : -1;
				int count = s.getUByte() + 1;
				int[] arr = new int[count + 1];
				for (int i = 0; i < count; ++i)
					arr[i] = s.getUShort();
				arr[count] = ending;
				FieldExtractor.setValue(obj, type, type, "scriptId", script == 0xFFFF ? -1 : script);
				FieldExtractor.setValue(obj, type, type, "configId", config == 0xFFFF ? -1 : config);
				FieldExtractor.setValue(obj, type, type, "childrenIds", arr);
			}
		}.addSelfToGroup(protocol);

		new StaticLocReader(102) {
			@Override
			public void read(Object destination, int type, Stream s) {
				int F = s.getUByte();
				int y = 0;
				int D = F;
				while (D != 0) {
					y++;
					D >>= 1;
				}
				for (int B = 0; B < y; B++) {
					if ((F & (1 << B)) != 0) {
						s.getBigSmart();
						s.getSmart();
					}
				}
			}
		}.addSelfToGroup(protocol);

		new BasicProtocol(new FieldExtractor[]{new ArrayExtractor(ParseType.UBYTE,0,new StreamExtractor[]{ParseType.SHORT, ParseType.SHORT}, null)}, 41).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.USHORT, "combatLevel")}, 95).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.STRING, "actions")}, 30, 31, 32, 33, 34).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new ArrayExtractor(ParseType.UBYTE,0,new StreamExtractor[]{ParseType.UBYTE, ParseType.BYTE, ParseType.BYTE, ParseType.BYTE}, null)}, 121).addSelfToGroup(protocol);
		new ExtraAttributeReader().addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.SMART), new FieldExtractor(ParseType.SMART), new FieldExtractor(ParseType.SMART), new FieldExtractor(ParseType.SMART), new FieldExtractor(ParseType.SMART), new FieldExtractor(ParseType.SMART)}, 179).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.BIG_SMART)}, 139).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(new StaticExtractor(false))}, 109, 111, 169, 178).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new ArrayExtractor(ParseType.UBYTE,0,new StreamExtractor[]{ParseType.BYTE}, null)}, 42).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(new StaticExtractor(0))}, 159).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.USHORT), new FieldExtractor(ParseType.USHORT), new FieldExtractor(ParseType.USHORT), new FieldExtractor(ParseType.USHORT), new FieldExtractor(ParseType.UBYTE)}, 134).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.USHORT), new FieldExtractor(ParseType.UBYTE)}, 181).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.BYTE), new FieldExtractor(ParseType.BYTE)}, 114).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.BIG_SMART, "headIcon")}, 138).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.BYTE)}, 125).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.USHORT), new FieldExtractor(ParseType.USHORT)}, 113, 164).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new ArrayExtractor(ParseType.UBYTE,0,new StreamExtractor[]{ParseType.USHORT}, null)}, 160).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new ArrayExtractor(ParseType.UBYTE,0,new StreamExtractor[]{ParseType.BIG_SMART}, null)}, 1, 60).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.STRING, "actions")}, 150, 151, 152, 153, 154).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(new StaticExtractor(1))}, 158).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.UBYTE)}, 12, 39, 100, 101, 119, 128, 140, 163, 165, 168, 180).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.USHORT)}, 44, 45, 97, 98, 103, 123, 127, 137, 142, 170, 171, 172, 173, 174, 175).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.STRING, "name")}, 2).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(new StaticExtractor(true))}, 99, 141, 143, 162).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(new StaticExtractor(false), "visible")}, 93).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(ParseType.BYTE), new FieldExtractor(ParseType.BYTE), new FieldExtractor(ParseType.BYTE), new FieldExtractor(ParseType.BYTE)}, 155).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new ArrayExtractor(ParseType.UBYTE,0,new StreamExtractor[]{ParseType.USHORT, ParseType.USHORT}, null)}, 40).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[]{new FieldExtractor(new StaticExtractor(false), "clickable")}, 107).addSelfToGroup(protocol);

	}

}