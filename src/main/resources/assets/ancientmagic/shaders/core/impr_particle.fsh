#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 color = vec4(0);
    for (int i = -4; i < 4; i++)
        for (int j = -3; j < 3; j++)
            color += texture(Sampler0, texCoord0 + vec2(j, i));

    if (color.a < 0.1) discard;

    float time = GameTime * 240000;
    float bright = sin(time / 20.0) * 0.5 + 0.15;
    float alpha = min(1, sin(time / 30.0) * 0.5 + 0.75);

    color = vec4((color * color * 0.0005 * bright + texture(Sampler0, texCoord0)).rgb, alpha * vertexColor.a);
    color *= ColorModulator;

    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
