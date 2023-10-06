_default:
	@just --list

run:
	gradle run -q --console=plain

lint_all:
	pre-commit run --all-files

todo:
	rg ".(TODO|FIXME|FIX|HACK|WARN|PREF|NOTE): " --glob !{{ file_name(justfile()) }}

# vim: set ft=make :
