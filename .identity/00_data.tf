
data "azurerm_user_assigned_identity" "identity_cd_01"{
  name = "${local.prefix}-${var.env_short}-${local.domain}-01-github-cd-identity"
  resource_group_name = "${local.prefix}-${var.env_short}-identity-rg"
}


data "github_organization_teams" "all" {
  root_teams_only = true
  summary_only    = true
}

data "azurerm_key_vault" "key_vault" {
  name                = "pagopa-${var.env_short}-kv"
  resource_group_name = "pagopa-${var.env_short}-sec-rg"
}

data "azurerm_key_vault" "domain_key_vault" {
  name                = "pagopa-${var.env_short}-${local.domain}-kv"
  resource_group_name = "pagopa-${var.env_short}-${local.domain}-sec-rg"
}

data "azurerm_key_vault_secret" "key_vault_sonar" {
  name         = "sonar-token"
  key_vault_id = data.azurerm_key_vault.key_vault.id
}

data "azurerm_key_vault_secret" "key_vault_bot_token" {
  name         = "bot-token-github"
  key_vault_id = data.azurerm_key_vault.key_vault.id
}

data "azurerm_key_vault_secret" "key_vault_cucumber_token" {
  name         = "cucumber-token"
  key_vault_id = data.azurerm_key_vault.key_vault.id
}

data "azurerm_key_vault_secret" "key_vault_integration_test_subkey" {
  name         = "integration-test-subkey"
  key_vault_id = data.azurerm_key_vault.key_vault.id
}

data "azurerm_user_assigned_identity" "workload_identity_clientid" {
  name                = "selfcare-workload-identity"
  resource_group_name = "pagopa-${var.env_short}-weu-${var.env}-aks-rg"
}