import { useState } from "react";

const CARD_W = 240;

const COLS = [40, 340, 640, 940];
const ROWS = [40, 290, 560];

const classes = [
  {
    id: "CarType", col: 0, row: 0,
    stereotype: "«enumeration»",
    name: "CarType",
    attributes: ["COMPACT", "SUV"],
    methods: [],
    accent: "#b45309", bg: "#fffbeb",
  },
  {
    id: "Car", col: 1, row: 0,
    stereotype: "",
    name: "Car",
    attributes: ["– licensePlate : String", "– carType : CarType"],
    methods: ["+ getLicensePlate() : String", "+ getCarType() : CarType", "+ equals() / hashCode()"],
    accent: "#1d4ed8", bg: "#eff6ff",
  },
  {
    id: "Customer", col: 2, row: 0,
    stereotype: "",
    name: "Customer",
    attributes: ["– customerId : String", "– name : String", "– email : String"],
    methods: ["+ getCustomerId() : String", "+ getName() : String", "+ getEmail() : String", "+ equals() / hashCode()"],
    accent: "#15803d", bg: "#f0fdf4",
  },
  {
    id: "ScanMode", col: 0, row: 1,
    stereotype: "«enumeration»",
    name: "ScanMode",
    attributes: ["ENTRY_ONLY", "ENTRY_EXIT"],
    methods: [],
    accent: "#b45309", bg: "#fffbeb",
  },
  {
    id: "ParkingLot", col: 1, row: 1,
    stereotype: "",
    name: "ParkingLot",
    attributes: ["– lotId : String", "– name : String", "– hourlyRate : double", "– dailyRate : double", "– scanMode : ScanMode"],
    methods: ["+ calculateCharge(CarType,double,int) : double", "+ getLotId() : String", "+ equals() / hashCode()"],
    accent: "#c2410c", bg: "#fff7ed",
  },
  {
    id: "ParkingPermit", col: 2, row: 1,
    stereotype: "",
    name: "ParkingPermit",
    attributes: ["– permitId : String", "– customer : Customer", "– car : Car"],
    methods: ["+ getPermitId() : String", "+ getCustomer() : Customer", "+ getCar() : Car", "+ equals() / hashCode()"],
    accent: "#7c3aed", bg: "#faf5ff",
  },
  {
    id: "ParkingOffice", col: 3, row: 1,
    stereotype: "",
    name: "ParkingOffice",
    attributes: ["– officeName : String", "– customers : Map<String,Customer>", "– permits : Map<String,ParkingPermit>", "– lots : Map<String,ParkingLot>", "– transactions : List<ParkingTransaction>"],
    methods: [
      "+ addCustomer(Customer) : Customer",
      "+ getCustomerIds() : Collection<String>",
      "+ addPermit(Customer,Car) : ParkingPermit",
      "+ getPermitIds() : Collection<String>",
      "+ getPermitIds(Customer) : Collection<String>",
      "+ park(...) : ParkingTransaction",
      "+ exit(ParkingTransaction, LocalDateTime)",
      "+ getTotalCharges(Customer) : double",
    ],
    accent: "#0369a1", bg: "#f0f9ff",
  },
  {
    id: "ParkingTransaction", col: 1, row: 2,
    stereotype: "",
    name: "ParkingTransaction",
    attributes: ["– transactionId : String", "– permit : ParkingPermit", "– lot : ParkingLot", "– entryTime : LocalDateTime", "– exitTime : LocalDateTime", "– charge : double"],
    methods: ["+ recordExit(LocalDateTime) : void", "+ getCharge() : double", "+ equals() / hashCode()"],
    accent: "#be123c", bg: "#fff1f2",
  },
];

function cardHeight(cls) {
  const headerH = cls.stereotype ? 58 : 44;
  const attrH = 10 + cls.attributes.length * 22;
  const methH = cls.methods.length > 0 ? 10 + cls.methods.length * 22 : 0;
  return headerH + attrH + methH + 10;
}

export default function ClassDiagram() {
  const [sel, setSel] = useState(null);
  const selCls = classes.find(c => c.id === sel);

  const pos = {};
  classes.forEach(c => {
    const x = COLS[c.col];
    const y = ROWS[c.row];
    const h = cardHeight(c);
    pos[c.id] = { x, y, h, cx: x + CARD_W / 2, cy: y + h / 2, bottom: y + h, right: x + CARD_W };
  });

  // All arrows use only horizontal and vertical segments
  const arrows = [
    // Car → CarType  (same row, right edge of CarType → left edge of Car)
    {
      id: "car-cartype", label: "uses", dash: true, comp: false,
      d: `M${pos.Car.x},${pos.Car.y + 24} H${pos.CarType.right}`,
      lx: (pos.Car.x + pos.CarType.right) / 2, ly: pos.Car.y + 16,
    },
    // ScanMode ← ParkingLot  (same row, left edge of ParkingLot → right edge of ScanMode)
    {
      id: "lot-scanmode", label: "uses", dash: true, comp: false,
      d: `M${pos.ParkingLot.x},${pos.ParkingLot.cy} H${pos.ScanMode.right}`,
      lx: (pos.ParkingLot.x + pos.ScanMode.right) / 2, ly: pos.ParkingLot.cy - 12,
    },
    // ParkingPermit → Car  (straight up, left-of-center)
    {
      id: "permit-car", label: "1 car", dash: false, comp: false,
      d: `M${pos.ParkingPermit.cx - 30},${pos.ParkingPermit.y} V${pos.Car.bottom}`,
      lx: pos.ParkingPermit.cx - 60, ly: (pos.ParkingPermit.y + pos.Car.bottom) / 2,
    },
    // ParkingPermit → Customer  (straight up, right-of-center)
    {
      id: "permit-customer", label: "1 customer", dash: false, comp: false,
      d: `M${pos.ParkingPermit.cx + 30},${pos.ParkingPermit.y} V${pos.Customer.bottom}`,
      lx: pos.ParkingPermit.cx + 80, ly: (pos.ParkingPermit.y + pos.Customer.bottom) / 2,
    },
    // ParkingTransaction → ParkingLot  (straight up)
    {
      id: "tx-lot", label: "1 lot", dash: false, comp: false,
      d: `M${pos.ParkingTransaction.cx - 20},${pos.ParkingTransaction.y} V${pos.ParkingLot.bottom}`,
      lx: pos.ParkingTransaction.cx - 70, ly: (pos.ParkingTransaction.y + pos.ParkingLot.bottom) / 2,
    },
    // ParkingTransaction → ParkingPermit  (up then right)
    {
      id: "tx-permit", label: "1 permit", dash: false, comp: false,
      d: `M${pos.ParkingTransaction.cx + 20},${pos.ParkingTransaction.y} V${pos.ParkingPermit.bottom + 20} H${pos.ParkingPermit.cx} V${pos.ParkingPermit.bottom}`,
      lx: pos.ParkingTransaction.cx + 70, ly: pos.ParkingTransaction.y - 14,
    },
    // ParkingOffice → Customer  (up then left from top of ParkingOffice)
    {
      id: "office-customer", label: "manages *", dash: false, comp: true,
      d: `M${pos.ParkingOffice.cx},${pos.ParkingOffice.y} V${pos.Customer.cy} H${pos.Customer.right}`,
      lx: pos.ParkingOffice.cx + 10, ly: pos.ParkingOffice.y - 14,
    },
    // ParkingOffice → ParkingPermit  (horizontal left, upper)
    {
      id: "office-permit", label: "issues *", dash: false, comp: true,
      d: `M${pos.ParkingOffice.x},${pos.ParkingOffice.cy - 30} H${pos.ParkingPermit.right}`,
      lx: (pos.ParkingOffice.x + pos.ParkingPermit.right) / 2, ly: pos.ParkingOffice.cy - 42,
    },
    // ParkingOffice → ParkingLot  (horizontal left, lower)
    {
      id: "office-lot", label: "manages *", dash: false, comp: true,
      d: `M${pos.ParkingOffice.x},${pos.ParkingOffice.cy + 10} H${pos.ParkingLot.right}`,
      lx: (pos.ParkingOffice.x + pos.ParkingLot.right) / 2, ly: pos.ParkingOffice.cy - 2,
    },
    // ParkingOffice → ParkingTransaction  (down then left)
    {
      id: "office-tx", label: "records *", dash: false, comp: true,
      d: `M${pos.ParkingOffice.cx - 20},${pos.ParkingOffice.bottom} V${pos.ParkingTransaction.cy} H${pos.ParkingTransaction.right}`,
      lx: pos.ParkingOffice.cx + 10, ly: pos.ParkingOffice.bottom + 16,
    },
  ];

  const SVG_W = 1230;
  const SVG_H = 820;

  return (
    <div style={{ background: "#f1f5f9", minHeight: "100vh", padding: 20, fontFamily: "Georgia, serif" }}>
      <div style={{ maxWidth: SVG_W + 40, margin: "0 auto" }}>
        <h1 style={{ fontSize: 20, fontWeight: 700, color: "#0f172a", margin: "0 0 4px" }}>
          University Parking System — UML Class Diagram
        </h1>
        <p style={{ fontSize: 12, color: "#64748b", margin: "0 0 14px" }}>Click any class to inspect its full definition</p>

        <div style={{ background: "white", borderRadius: 14, boxShadow: "0 2px 20px rgba(0,0,0,0.07)", overflowX: "auto" }}>
          <svg width={SVG_W} height={SVG_H}>
            <defs>
              <marker id="arrowGray" markerWidth="9" markerHeight="9" refX="8" refY="3" orient="auto">
                <path d="M0,0 L0,6 L9,3 z" fill="#94a3b8" />
              </marker>
              <marker id="arrowBlue" markerWidth="9" markerHeight="9" refX="8" refY="3" orient="auto">
                <path d="M0,0 L0,6 L9,3 z" fill="#0369a1" />
              </marker>
              <marker id="diamondWhite" markerWidth="14" markerHeight="14" refX="1" refY="7" orient="auto">
                <path d="M1,7 L6,2 L11,7 L6,12 z" fill="white" stroke="#0369a1" strokeWidth="1.8" />
              </marker>
            </defs>

            {/* Draw arrows first */}
            {arrows.map(a => (
              <g key={a.id}>
                <path
                  d={a.d} fill="none"
                  stroke={a.comp ? "#0369a1" : "#94a3b8"}
                  strokeWidth={a.comp ? 1.8 : 1.4}
                  strokeDasharray={a.dash ? "6,3" : "none"}
                  markerEnd={a.comp ? "url(#diamondWhite)" : "url(#arrowGray)"}
                />
                <text x={a.lx} y={a.ly} fontSize={10} fill="#64748b" fontStyle="italic" textAnchor="middle">{a.label}</text>
              </g>
            ))}

            {/* Draw class boxes */}
            {classes.map(cls => {
              const { x, y, h } = pos[cls.id];
              const headerH = cls.stereotype ? 58 : 44;
              const attrH = 10 + cls.attributes.length * 22;
              const isSelected = sel === cls.id;

              return (
                <g key={cls.id} onClick={() => setSel(sel === cls.id ? null : cls.id)} style={{ cursor: "pointer" }}>
                  {/* Drop shadow */}
                  <rect x={x + 4} y={y + 4} width={CARD_W} height={h} rx={8} fill="rgba(0,0,0,0.05)" />
                  {/* Card body */}
                  <rect x={x} y={y} width={CARD_W} height={h} rx={8}
                    fill={cls.bg}
                    stroke={cls.accent}
                    strokeWidth={isSelected ? 2.5 : 1.5}
                    strokeOpacity={isSelected ? 1 : 0.6}
                  />
                  {/* Header fill */}
                  <clipPath id={`clip-${cls.id}`}>
                    <rect x={x} y={y} width={CARD_W} height={headerH} rx={8} />
                  </clipPath>
                  <rect x={x} y={y} width={CARD_W} height={headerH}
                    fill={cls.accent} fillOpacity={0.12} clipPath={`url(#clip-${cls.id})`} />

                  {/* Stereotype */}
                  {cls.stereotype && (
                    <text x={x + CARD_W / 2} y={y + 18} textAnchor="middle"
                      fontSize={9.5} fill={cls.accent} fontStyle="italic" fontFamily="Georgia,serif">
                      {cls.stereotype}
                    </text>
                  )}
                  {/* Class name */}
                  <text x={x + CARD_W / 2} y={cls.stereotype ? y + 40 : y + 28}
                    textAnchor="middle" fontSize={13} fontWeight="700"
                    fill={cls.accent} fontFamily="Georgia,serif">
                    {cls.name}
                  </text>

                  {/* Attr divider */}
                  <line x1={x} y1={y + headerH} x2={x + CARD_W} y2={y + headerH}
                    stroke={cls.accent} strokeWidth={1} strokeOpacity={0.35} />

                  {/* Attributes */}
                  {cls.attributes.map((attr, i) => (
                    <text key={i} x={x + 10} y={y + headerH + 18 + i * 22}
                      fontSize={10} fill="#374151" fontFamily="'Courier New',monospace">
                      {attr}
                    </text>
                  ))}

                  {/* Method divider */}
                  {cls.methods.length > 0 && (
                    <line x1={x} y1={y + headerH + attrH} x2={x + CARD_W} y2={y + headerH + attrH}
                      stroke={cls.accent} strokeWidth={1} strokeDasharray="3,3" strokeOpacity={0.3} />
                  )}

                  {/* Methods */}
                  {cls.methods.map((m, i) => (
                    <text key={i} x={x + 10} y={y + headerH + attrH + 18 + i * 22}
                      fontSize={9.5} fill="#1e40af" fontFamily="'Courier New',monospace">
                      {m}
                    </text>
                  ))}
                </g>
              );
            })}
          </svg>
        </div>

        {/* Legend */}
        <div style={{ display: "flex", gap: 24, marginTop: 12, fontSize: 11, color: "#475569", flexWrap: "wrap", alignItems: "center" }}>
          <span style={{ display: "flex", alignItems: "center", gap: 5 }}>
            <svg width={40} height={14} style={{ overflow: "visible" }}>
              <defs>
                <marker id="lgray" markerWidth="8" markerHeight="8" refX="7" refY="3" orient="auto">
                  <path d="M0,0 L0,6 L8,3 z" fill="#94a3b8" />
                </marker>
                <marker id="lblue" markerWidth="8" markerHeight="8" refX="7" refY="3" orient="auto">
                  <path d="M0,0 L0,6 L8,3 z" fill="#0369a1" />
                </marker>
                <marker id="ldiam" markerWidth="12" markerHeight="12" refX="1" refY="6" orient="auto">
                  <path d="M1,6 L5,2 L9,6 L5,10 z" fill="white" stroke="#0369a1" strokeWidth="1.5" />
                </marker>
              </defs>
              <line x1={2} y1={7} x2={32} y2={7} stroke="#0369a1" strokeWidth={1.8} markerEnd="url(#ldiam)" />
            </svg>
            Composition
          </span>
          <span style={{ display: "flex", alignItems: "center", gap: 5 }}>
            <svg width={40} height={14} style={{ overflow: "visible" }}>
              <line x1={2} y1={7} x2={32} y2={7} stroke="#94a3b8" strokeWidth={1.4} markerEnd="url(#lgray)" />
            </svg>
            Association
          </span>
          <span style={{ display: "flex", alignItems: "center", gap: 5 }}>
            <svg width={40} height={14} style={{ overflow: "visible" }}>
              <line x1={2} y1={7} x2={32} y2={7} stroke="#94a3b8" strokeWidth={1.4} strokeDasharray="5,3" markerEnd="url(#lgray)" />
            </svg>
            Dependency
          </span>
          <span>
            <span style={{ background: "#fffbeb", border: "1px solid #b45309", borderRadius: 4, padding: "2px 8px", fontStyle: "italic" }}>«enumeration»</span>
            &nbsp;Enum
          </span>
        </div>

        {/* Inspector panel */}
        {selCls && (
          <div style={{
            marginTop: 14, background: "white", borderRadius: 10,
            border: `2px solid ${selCls.accent}`,
            padding: "14px 20px",
            boxShadow: `0 4px 20px ${selCls.accent}25`,
          }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 10 }}>
              <h2 style={{ margin: 0, fontSize: 15, fontWeight: 700, color: selCls.accent, fontFamily: "Georgia,serif" }}>
                {selCls.stereotype && <span style={{ fontWeight: 400, fontSize: 11, fontStyle: "italic", marginRight: 6 }}>{selCls.stereotype}</span>}
                {selCls.name}
              </h2>
              <button onClick={() => setSel(null)}
                style={{ background: "none", border: "none", cursor: "pointer", color: "#94a3b8", fontSize: 18, lineHeight: 1 }}>✕</button>
            </div>
            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>
              <div>
                <p style={{ margin: "0 0 6px", fontSize: 10, fontWeight: 700, color: "#94a3b8", textTransform: "uppercase", letterSpacing: "0.08em" }}>Attributes</p>
                {selCls.attributes.map((a, i) => (
                  <p key={i} style={{ margin: "3px 0", fontFamily: "monospace", fontSize: 11, color: "#374151" }}>{a}</p>
                ))}
              </div>
              <div>
                <p style={{ margin: "0 0 6px", fontSize: 10, fontWeight: 700, color: "#94a3b8", textTransform: "uppercase", letterSpacing: "0.08em" }}>Methods</p>
                {selCls.methods.length ? selCls.methods.map((m, i) => (
                  <p key={i} style={{ margin: "3px 0", fontFamily: "monospace", fontSize: 11, color: "#1d4ed8" }}>{m}</p>
                )) : <p style={{ fontSize: 11, color: "#94a3b8", fontStyle: "italic" }}>None</p>}
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
