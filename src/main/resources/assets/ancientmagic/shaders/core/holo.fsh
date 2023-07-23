#version 150

uniform float time;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

float drawlines() {
    return sqrt(sin(gl_FragCoord.y + time * 10) * 0.5 + 0.5);
}

void main() {
    float timeScale = (sin(time * 2.5) + 1.0) * 0.5;
    timeScale = timeScale * 0.15 + 0.85;

    vec2 edge = abs(texCoord0.xy - 0.5) * 2.0;

    edge = edge * 0.25 + 0.75;

    float mul = pow(max(edge.x, edge.y), 8.0) * 8.0 + 0.2;

    vec4 _color = vertexColor * drawlines();
    _color *= timeScale;
    _color *= mul;
    fragColor = _color;
}