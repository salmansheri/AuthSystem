import { defineConfig } from "@hey-api/openapi-ts";


export default defineConfig({
    input: "./openApi.json",
    output: "src/generated",
    plugins: ["@hey-api/client-axios",   '@hey-api/typescript',   '@tanstack/react-query' ]
})