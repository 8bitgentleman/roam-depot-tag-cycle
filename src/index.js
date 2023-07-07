import { toggleRenderComponent } from "./entry-helpers";

const componentName = 'Tag Cycle Component'
const codeBlockUID = 'roam-render-tag-cycle-cljs';
const cssBlockUID = 'roam-render-tag-cycle-css';
const renderString = `{{[[roam/render]]:((${codeBlockUID}))`;
const replacementString = '{{tag-cycle}}';
const version = 'v5';
const titleblockUID = 'roam-render-tag-cycle';
const cssBlockParentUID = 'tag-cycle-css-parent';

function onload({extensionAPI}) {
  const panelConfig = {
    tabTitle: componentName,
    settings: [
      
    ]
  };

  extensionAPI.settings.panel.create(panelConfig);

  if (!roamAlphaAPI.data.pull("[*]", [":block/uid", titleblockUID])) {
    // component hasn't been loaded so we add it to the graph
    toggleRenderComponent(true, titleblockUID, cssBlockParentUID, version, renderString, replacementString, cssBlockUID, codeBlockUID, componentName)
  }

  console.log(`load ${componentName} plugin`)
}

function onunload() {
  console.log(`unload ${componentName} plugin`)
  toggleRenderComponent(false, titleblockUID, cssBlockParentUID, version, renderString, replacementString, cssBlockUID, codeBlockUID, componentName)
}

export default {
onload,
onunload
};
