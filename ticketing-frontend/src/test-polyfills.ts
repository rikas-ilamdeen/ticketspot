declare global {
  var global: typeof globalThis;
}

globalThis.global = globalThis;

export {};
